package com.gams.examples.transport;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.gams.api.GAMSCheckpoint;
import com.gams.api.GAMSDatabase;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSSet;
import com.gams.api.GAMSVariableRecord;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example shows how to create and use save/restart file. The example 
 * runs the jobs  with the GAMS [trnsport] model from the GAMS Model Library. 
 */
public class Transport11 {

    public static void main(String[] args) {
        // check workspace info from command line arguments
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0) {
            wsInfo.setSystemDirectory( args[0] );
        }
        // create a directory
        File workingDirectory = new File(System.getProperty("user.dir"), "Transport11");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        // create a workspace
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);

        // Create a save/restart file usually supplied by an application provider
        // We create it for demonstration purpose
        CreateSaveRestart( ws, "tbase" );

        // define some data by using Java data structures
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

        ws = new GAMSWorkspace(wsInfo);

        // prepare a GAMSDatabase with data from the Java data structures
        GAMSDatabase db = ws.addDatabase();

        GAMSSet i = db.addSet("i", 1, "canning plants");
        for (String p : plants)
            i.addRecord(p);

        GAMSSet j = db.addSet("j", 1, "markets");
        for (String m : markets)
            j.addRecord(m);

        GAMSParameter a = db.addParameter("a", "capacity of plant i in cases", i);
        for (String p : plants)
            a.addRecord(p).setValue( capacity.get(p) );

        GAMSParameter b = db.addParameter("b", "demand at market j in cases", j);
        for (String m : markets)
            b.addRecord(m).setValue( demand.get(m) );

        GAMSParameter d = db.addParameter("d", "distance in thousands of miles", i, j);
        for(Vector<String> vd : distance.keySet())
            d.addRecord(vd).setValue( distance.get(vd).doubleValue() );

        GAMSParameter f = db.addParameter("f", "freight in dollars per case per thousand miles");
        f.addRecord().setValue( 90 );

        // run a job using data from the created GAMSDatabase
        GAMSCheckpoint cpBase = ws.addCheckpoint("tbase");

        GAMSOptions opt = ws.addOptions();
        GAMSJob t11 = ws.addJobFromString(model, cpBase);
        opt.defines("gdxincname", db.getName());
        opt.setAllModelTypes("xpress");
        t11.run(opt, db);

        for (GAMSVariableRecord rec : t11.OutDB().getVariable("x"))
             System.out.println("x(" + rec.getKey(0) + "," + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());
    }

    static void CreateSaveRestart(GAMSWorkspace ws, String cpFileName) {
        //GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        //wsInfo.setWorkingDirectory(workingDirectory);
        //GAMSWorkspace ws = new GAMSWorkspace(wsInfo);

        GAMSJob j1 = ws.addJobFromString(baseModel);
        GAMSOptions opt = ws.addOptions();

        opt.setAction( GAMSOptions.EAction.CompileOnly );

        GAMSCheckpoint cp = ws.addCheckpoint(cpFileName);
        j1.run(opt, cp);

        opt.dispose();
    }

    static String baseModel =
             "$onempty                                                            \n"+
             " Sets                                                               \n"+
             "      i(*)   canning plants / /                                     \n"+
             "      j(*)   markets        / /                                     \n"+
             "                                                                    \n"+
             " Parameters                                                         \n"+
             "      a(i)   capacity of plant i in cases / /                       \n"+
             "      b(j)   demand at market j in cases  / /                       \n"+
             "      d(i,j) distance in thousands of miles / /                     \n"+
             " Scalar f  freight in dollars per case per thousand miles /0/;      \n"+
             "                                                                    \n"+
             " Parameter c(i,j)  transport cost in thousands of dollars per case ;\n"+
             "                                                                    \n"+
             "           c(i,j) = f * d(i,j) / 1000 ;                             \n"+
             "                                                                    \n"+
             " Variables                                                          \n"+
             "      x(i,j)  shipment quantities in cases                          \n"+
             "      z       total transportation costs in thousands of dollars ;  \n"+
             "                                                                    \n"+
             " Positive Variable x ;                                              \n"+
             "                                                                    \n"+
             " Equations                                                          \n"+
             "      cost        define objective function                         \n"+
             "      supply(i)   observe supply limit at plant i                   \n"+
             "      demand(j)   satisfy demand at market j ;                      \n"+
             "                                                                    \n"+
             " cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                 \n"+
             "                                                                    \n"+
             " supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                         \n"+
             "                                                                    \n"+
             " demand(j) ..   sum(i, x(i,j))  =g=  b(j) ;                         \n"+
             "                                                                    \n"+
             " Model transport /all/ ;                                            \n"+
             "                                                                    \n"+
             " Solve transport using lp minimizing z ;                            \n"+
             "                                                                    \n";

	static String model =
             "$if not set gdxincname $abort 'no include file name for data file provided' \n"+
             "$gdxin %gdxincname%                                                         \n"+
             "$onMulti                                                                    \n"+
             "$load i j a b d f                                                           \n"+
             "$gdxin                                                                      \n"+
             "                                                                            \n"+
             "Display x.l, x.m ;                                                          \n"+
             "                                                                            \n";
}
