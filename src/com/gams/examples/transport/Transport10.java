package com.gams.examples.transport;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

import com.gams.api.GAMSDatabase;
import com.gams.api.GAMSGlobals;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSSet;
import com.gams.api.GAMSVariableRecord;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 *  This example demonstrates how to retrieve an input for GAMS Transport Model
 *  from an Excel file (transport.xlsx) using JExcelApi, a open source java API
 *  for reading/writing Excel (See http://jexcelapi.sourceforge.net/ ).
 */

public class Transport10 {

    public static void main(String[] args) {
        // check workspace info from command line arguments
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0)
            wsInfo.setSystemDirectory( args[0] );
        // create a directory
        File workingDirectory = new File(System.getProperty("user.dir"), "Transport10");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        // create a workspace
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);

        // to find the directory where GAMS has been installed
        String gamsdir = ws.systemDirectory();

        // read input data from workbook "transport.xls"
        if (!gamsdir.endsWith(GAMSGlobals.FILE_SEPARATOR))
            gamsdir += GAMSGlobals.FILE_SEPARATOR;
        String input = gamsdir + "apifiles" + GAMSGlobals.FILE_SEPARATOR + "Data" + GAMSGlobals.FILE_SEPARATOR
                               + "transport.xls";

        File inputFile = new File(input);

        int iCount = 0;
        int jCount = 0;
        String[][] capacityData = null;
        String[][] demandData = null;
        String[][] distanceData = null;
    	Workbook w;
        try {
            w = Workbook.getWorkbook(inputFile);

            Sheet capacity = w.getSheet("capacity");
            capacityData = new String[capacity.getRows()][capacity.getColumns()];
            iCount = capacity.getColumns();

            for (int j = 0; j < capacity.getColumns(); j++)
                for (int i = 0; i < capacity.getRows(); i++)
                     capacityData[i][j] = capacity.getCell(j, i).getContents();

            Sheet demand = w.getSheet("demand");
            demandData = new String[demand.getRows()][demand.getColumns()];
            jCount = demand.getColumns();

            for (int j = 0; j < demand.getColumns(); j++)
                for (int i = 0; i < demand.getRows(); i++)
                    demandData[i][j] = demand.getCell(j, i).getContents();

            Sheet distance = w.getSheet("distance");
            distanceData = new String[distance.getRows()][distance.getColumns()];
            for (int j = 0; j < distance.getColumns(); j++)
                for (int i = 0; i < distance.getRows(); i++)
                     distanceData[i][j] = distance.getCell(j, i).getContents();

            w.close();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        // Creating the GAMSDatabase and fill with the workbook data
        GAMSDatabase db = ws.addDatabase();

        GAMSSet i = db.addSet("i", 1, "Plants");
        GAMSSet j = db.addSet("j", 1, "Markets");
        GAMSParameter capacityParam = db.addParameter("a", "Capacity", i);
        GAMSParameter demandParam = db.addParameter("b", "Demand", j);
        GAMSParameter distanceParam = db.addParameter("d", "Distance", i, j);

        for (int ic = 0; ic < iCount; ic++) {
            i.addRecord( capacityData[0][ic] );
            capacityParam.addRecord( capacityData[0][ic] ).setValue( Double.valueOf(capacityData[1][ic]).doubleValue() );
        }

        for (int jc = 0; jc < jCount; jc++)  {
            j.addRecord( demandData[0][jc] );
            demandParam.addRecord( demandData[0][jc] ).setValue( Double.valueOf(demandData[1][jc]).doubleValue() );
            String[] data = null;
            for (int ic = 0; ic < iCount; ic++) {
            	data =  new String[] { distanceData[ic+1][0], distanceData[0][jc+1] };
                distanceParam.addRecord( data ).setValue( Double.valueOf(distanceData[ic+1][jc+1]) );
            }
        }

        // Create and run the GAMSJob
        GAMSOptions opt = ws.addOptions();
        GAMSJob t10 = ws.addJobFromString(model);
        opt.defines("gdxincname", db.getName());
        opt.setAllModelTypes("xpress");
        t10.run(opt, db);
        for (GAMSVariableRecord rec : t10.OutDB().getVariable("x"))
            System.out.println("x(" + rec.getKey(0) + "," + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());

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
