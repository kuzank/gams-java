package com.gams.examples.transport;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.gams.api.GAMSDatabase;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSSet;
import com.gams.api.GAMSVariable;
import com.gams.api.GAMSVariableRecord;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 *  This example demonstrates how to retrieve an input for GAMS Transport Model
 *  from a MSAccess file (transport.accdb) using standard JDBC library.
 */
public class Transport9 {

    public static void main(String[] args) {
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0)
            wsInfo.setSystemDirectory( args[0] );

        File workingDirectory = new File(System.getProperty("user.dir"), "Transport9");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());

        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);
        GAMSDatabase db = readDataFromAccess(ws);

        GAMSOptions opt = ws.addOptions();
        GAMSJob t9 = ws.addJobFromString( model );
        opt.defines("gdxincname", db.getName());
        opt.setAllModelTypes( "xpress" );
        t9.run(opt, db);
        for (GAMSVariableRecord rec : t9.OutDB().getVariable("x"))
            System.out.println("x(" + rec.getKey(0) + "," + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());

        writeDataToAccess(ws, t9.OutDB());
    }

    static GAMSDatabase readDataFromAccess(GAMSWorkspace ws)  {
        GAMSDatabase db = ws.addDatabase();

        try {
            // loading the jdbc odbc driver
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            // creating connection to  database
            Connection c = DriverManager.getConnection("jdbc:odbc:transportdsn","","");

             // read GAMS sets
             readSet(c, db, "SELECT Plant FROM Plant", "i", 1, "canning plants");
             readSet(c, db, "SELECT Market FROM Market", "j", 1, "markets");

             // read GAMS parameters
             readParameter(c, db, "SELECT Plant, Capacity FROM Plant", "a", 1, "capacity of plant i in cases");
             readParameter(c, db, "SELECT Market,Demand FROM Market", "b", 1, "demand at market j in cases");
             readParameter(c, db, "SELECT Plant,Market,Distance FROM Distance", "d", 2, "distance in thousands of miles");

             c.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Failed to find a driver for the database.");
            e.printStackTrace();
            System.exit(-1);
        } catch (SQLException e) {
             System.err.println("Error: Failed to retrieve data from the database.");
             e.printStackTrace();
             System.exit(-1);
        }
        return db;
    }

    static void readSet(Connection c, GAMSDatabase db, String queryString, String setName, int setDimension, String setExplanatoryText) throws SQLException {
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery(queryString);
        ResultSetMetaData rsmd = rs.getMetaData();

        if (rsmd.getColumnCount() != setDimension) {
            System.err.println("Error: Number of fields in select statement does not match setDimemsion.");
            c.close();
            System.exit(-1);
        }

        GAMSSet set = db.addSet(setName, setDimension, setExplanatoryText);

        String[] keys = new String[setDimension];

        while (rs.next()) {
           for (int idx=0; idx < setDimension; idx++)
               keys[idx] = rs.getString(idx+1);
           set.addRecord( keys );
        }
        st.close();
    }

    static void readParameter(Connection c, GAMSDatabase db, String queryString, String parName, int parDimension, String parExplanatoryText) throws SQLException {
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery(queryString);
        ResultSetMetaData rsmd = rs.getMetaData();

        int numberOfColumns = rsmd.getColumnCount();
        if (numberOfColumns != (parDimension+1)) {
            System.err.println("Error: Number of fields in select statement does not match parDimension.");
            c.close();
            System.exit(-1);
        }

        GAMSParameter parameter = db.addParameter(parName, parDimension, parExplanatoryText);

        String[] keys = new String[parDimension];

        while (rs.next()) {
            for (int idx=0; idx < parDimension; idx++)
        		keys[idx] = rs.getString(idx+1);
    	    parameter.addRecord( keys ).setValue( Double.valueOf(rs.getString(numberOfColumns)) );
        }
        st.close();
    }

    static void writeVariable(Connection c, GAMSDatabase db, String varName, String ... domains) throws SQLException {         

        GAMSVariable var = db.getVariable(varName);
        if ( domains.length != var.getDimension() ) {
             System.err.println("Error: Number of column names does not match the dimension of the variable.");
             c.close();
             System.exit(-1);
        }

        Statement st = c.createStatement();

        String sql = "create table " + varName + "(";
        for (String dom : domains)
            sql += dom + " varchar(64), ";
        sql += "lvl double)";

        st.executeUpdate(sql);

        for (GAMSVariableRecord rec : var) {
            sql = "insert into " + varName + "(";
            for (String dom : domains)
                sql += dom + ", ";
            sql += "lvl) values (";
            for (String key : rec.getKeys())
                sql += "'" + key + "', ";
            sql += rec.getLevel() + ")";

            st.executeUpdate(sql);
        }
        st.close();
     }

     static void writeDataToAccess(GAMSWorkspace ws, GAMSDatabase db) {
        try {
            // loading the jdbc odbc driver
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            // creating connection to database
            Connection c = DriverManager.getConnection("jdbc:odbc:transportdsn","","");

            // write levels of variable x
            writeVariable(c, db, "x", "i", "j");

            c.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Failed to find a driver for the database.");
            e.printStackTrace();
            System.exit(-1);
        } catch (SQLException e) {
             System.err.println("Error: Failed to write data back to the database.");
             e.printStackTrace();
             System.exit(-1);
        } 
    }

    static String model =
            " Sets                                                                       \n"+
            "      i   canning plants                                                    \n"+
            "      j   markets                                                           \n"+
            "                                                                            \n"+
            " Parameters                                                                 \n"+
            "      a(i)   capacity of plant i in cases                                   \n"+
            "      b(j)   demand at market j in cases                                    \n"+
            "      d(i,j) distance in thousands of miles                                 \n"+
            " Scalar f  freight in dollars per case per thousand miles /90/;             \n"+
            "                                                                            \n"+
            "$if not set gdxincname $abort 'no include file name for data file provided' \n"+
            "$gdxin %gdxincname%                                                         \n"+
            "$load i j a b d                                                             \n"+
            "$gdxin                                                                      \n"+
            "                                                                            \n"+
            " Parameter c(i,j)  transport cost in thousands of dollars per case ;        \n"+
            "                                                                            \n"+
            "           c(i,j) = f * d(i,j) / 1000 ;                                     \n"+
            "                                                                            \n"+
            " Variables                                                                  \n"+
            "      x(i,j)  shipment quantities in cases                                  \n"+
            "      z       total transportation costs in thousands of dollars ;          \n"+
            "                                                                            \n"+
            " Positive Variable x ;                                                      \n"+
            "                                                                            \n"+
            " Equations                                                                  \n"+
            "      cost        define objective function                                 \n"+
            "      supply(i)   observe supply limit at plant i                           \n"+
            "      demand(j)   satisfy demand at market j ;                              \n"+
            "                                                                            \n"+
            " cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                         \n"+
            "                                                                            \n"+
            " supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                                 \n"+
            "                                                                            \n"+
            " demand(j) ..   sum(i, x(i,j))  =g=  b(j) ;                                 \n"+
            "                                                                            \n"+
            " Model transport /all/ ;                                                    \n"+
            "                                                                            \n"+
            " Solve transport using lp minimizing z ;                                    \n"+
            "                                                                            \n"+
            " Display x.l, x.m ;                                                         \n"+
            "                                                                            \n";
}
