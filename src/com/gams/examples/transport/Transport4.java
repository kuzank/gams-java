package com.gams.examples.transport;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
 * This example shows how to prepare a GAMSDatabase from Java data structures. 
 * The example runs a job with the GAMS [trnsport] model from the GAMS Model 
 * Library. 
 */
public class Transport4 {

    public static void main(String[] args) {
        // check workspace info from command line arguments
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0)
            wsInfo.setSystemDirectory( args[0] );
        // create a directory
        File workingDirectory = new File(System.getProperty("user.dir"), "Transport4");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        // create a workspace
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);

        // prepare input data
        List<String> plants = Arrays.asList("Seattle", "San-Diego");
        List<String> markets = Arrays.asList("New-York", "Chicago", "Topeka");
        Map<String, Double> capacity = new HashMap<String, Double>();
        {
             capacity.put("Seattle", Double.valueOf(350.0));
             capacity.put("San-Diego", Double.valueOf(600.0));
        }
        Map<String, Double> demand = new HashMap<String, Double>();
        {
             demand.put("New-York", Double.valueOf(325.0));
             demand.put("Chicago", Double.valueOf(300.0));
             demand.put("Topeka", Double.valueOf(275.0));
        }

        Map<Vector<String>, Double> distance = new HashMap<Vector<String>, Double>();
        {
            distance.put( new Vector<String>( Arrays.asList(new String[]{"Seattle", "New-York"}) ), Double.valueOf(2.5));        
            distance.put( new Vector<String>( Arrays.asList(new String[]{"Seattle", "Chicago"}) ),  Double.valueOf(1.7));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"Seattle", "Topeka"}) ),  Double.valueOf(1.8));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"San-Diego", "New-York"}) ), Double.valueOf(2.5));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"San-Diego", "Chicago"}) ),  Double.valueOf(1.8));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"San-Diego", "Topeka"}) ),   Double.valueOf(1.4));
        }

        // add a database and add input data into the database
        GAMSDatabase db = ws.addDatabase();

        GAMSSet i = db.addSet("i", 1, "canning plants");
        for(String p : plants)
            i.addRecord(p);

        GAMSSet j = db.addSet("j", 1, "markets");
        for(String m : markets)
            j.addRecord(m);

        GAMSParameter a = db.addParameter("a", "capacity of plant i in cases", i);
        for (String p : plants) {
           a.addRecord(p).setValue( capacity.get(p) );
        }

        GAMSParameter b = db.addParameter("b", "demand at market j in cases", j);
        for(String m : markets)
            b.addRecord(m).setValue( demand.get(m) );

        GAMSParameter d = db.addParameter("d", "distance in thousands of miles", i, j);
        for(Vector<String> vd : distance.keySet())
            d.addRecord(vd).setValue( distance.get(vd).doubleValue() );

        GAMSParameter f = db.addParameter("f", "freight in dollars per case per thousand miles");
        f.addRecord().setValue( 90 );

        // create and run a job from the model and read gdx include file from the database
        GAMSJob t4 = ws.addJobFromString(model);
        GAMSOptions opt = ws.addOptions();
        opt.defines("gdxincname", db.getName());

        t4.run(opt, db);

        GAMSVariable var = t4.OutDB().getVariable("x");
        for(GAMSVariableRecord rec : var)
            System.out.println("x(" + rec.getKey(0) + ", " + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());
        System.out.println();

        // set option of all model types for xpress and run the job again
        opt.setAllModelTypes("xpress");
        t4.run(opt, db);

        for(GAMSVariableRecord rec : t4.OutDB().getVariable("x"))
            System.out.println("x(" + rec.getKey(0) + "," + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());

    }

    static String model =
            "Sets                                                                       \n" +
            "      i   canning plants                                                   \n" +
            "      j   markets                                                          \n" +
            "                                                                           \n" +
            " Parameters                                                                \n" +
            "      a(i)   capacity of plant i in cases                                  \n" +
            "      b(j)   demand at market j in cases                                   \n" +
            "      d(i,j) distance in thousands of miles                                \n" +
            " Scalar f  freight in dollars per case per thousand miles;                 \n" +
            "                                                                           \n" +
            "$if not set gdxincname $abort 'no include file name for data file provided'\n" +
            "$gdxin %gdxincname%                                                        \n" +
            "$load i j a b d f                                                          \n" +
            "$gdxin                                                                     \n" +
            "                                                                           \n" +
            " Parameter c(i,j)  transport cost in thousands of dollars per case ;       \n" +
            "                                                                           \n" +
            "            c(i,j) = f * d(i,j) / 1000 ;                                   \n" +
            "                                                                           \n" +
            " Variables                                                                 \n" +
            "       x(i,j)  shipment quantities in cases                                \n" +
            "       z       total transportation costs in thousands of dollars ;        \n" +
            "                                                                           \n" +
            " Positive Variable x ;                                                     \n" +
            "                                                                           \n" +
            " Equations                                                                 \n" +
            "                                                                           \n" +
            "      cost        define objective function                                \n" +
            "      supply(i)   observe supply limit at plant i                          \n" +
            "      demand(j)   satisfy demand at market j ;                             \n" +
            "                                                                           \n" +
            "  cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                       \n" +
            "                                                                           \n" +
            "  supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                               \n" +
            "                                                                           \n" +
            "  demand(j) ..   sum(i, x(i,j))  =g=  b(j) ;                               \n" +
            "                                                                           \n" +
            " Model transport /all/ ;                                                   \n" +
            "                                                                           \n" +
            " Solve transport using lp minimizing z ;                                   \n" +
            "                                                                           \n" +
            " Display x.l, x.m ;                                                        \n" +
            "                                                                           \n";
}

