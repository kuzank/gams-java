package com.gams.examples.transport;

import java.io.File;

import com.gams.api.GAMSCheckpoint;
import com.gams.api.GAMSGlobals;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSModelInstance;
import com.gams.api.GAMSModifier;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSSetRecord;
import com.gams.api.GAMSVariable;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example shows how to create a GAMSModelInstance from a GAMSCheckpoint
 * and how to modify a parameter or a variable of a GAMSModelInstance using 
 * GAMSModifiler. The example runs the jobs with the GAMS [trnsport] model from 
 * the GAMS Model Library. 
 */
public class Transport7  {

    public static void main(String[] args)  {
        // check workspace info from command line arguments
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0) 
            wsInfo.setSystemDirectory( args[0] );
        // create a directory
        File workingDirectory = new File(System.getProperty("user.dir"), "Transport7");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        // create a workspace
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);
        // create a checkpoint
        GAMSCheckpoint cp = ws.addCheckpoint();

        // initialize a checkpoint by running a job
        GAMSJob t7 = ws.addJobFromString(model);
        t7.run(cp);

        // create a MoelInstance and solve it multiple times with different scalar bmult
        GAMSModelInstance mi = cp.addModelInstance();
        GAMSParameter bmult = mi.SyncDB().addParameter("bmult", "demand multiplier");
        GAMSOptions opt = ws.addOptions();
        opt.setAllModelTypes("cplexd");

        // instantiate the ModelInstance and pass a model definition and Modifier to declare bmult mutable
        mi.instantiate("transport use lp min z", opt, new GAMSModifier(bmult));

        bmult.addRecord().setValue( 1.0 );
        double[] bmultlist =  new double[] { 0.6, 0.7 , 0.8, 0.9, 1.0, 1.1, 1.2, 1.3 };

        for (double b : bmultlist) {
           bmult.getFirstRecord().setValue( b );
           mi.solve();
           System.out.println("Scenario bmult=" + b + ":");
           System.out.println("  Modelstatus: " + mi.getModelStatus());
           System.out.println("  Solvestatus: " + mi.getSolveStatus());
           System.out.println("  Obj: " + mi.SyncDB().getVariable("z").findRecord().getLevel());
        }

        // create a ModelInstance and solve it with single links in the network blocked
        mi = cp.addModelInstance();

        GAMSVariable x = mi.SyncDB().addVariable("x", 2, GAMSGlobals.VarType.POSITIVE, "");
        GAMSParameter xup = mi.SyncDB().addParameter("xup", 2, "upper bound on x");

        // instantiate the ModelInstance and pass a model definition and Modifier to declare upper bound of X mutable
        mi.instantiate("transport use lp min z", new GAMSModifier(x, GAMSGlobals.UpdateAction.UPPER, xup));

        for (GAMSSetRecord i : t7.OutDB().getSet("i")) {
            for (GAMSSetRecord j : t7.OutDB().getSet("j")) {
                 xup.clear();
                 String[] keys = { i.getKey(0), j.getKey(0) };
                 xup.addRecord(keys).setValue(0);
                 mi.solve();
                 System.out.println("Scenario link blocked: " + i.getKey(0)  + " - " + j.getKey(0));
                 System.out.println("  Modelstatus: " + mi.getModelStatus());
                 System.out.println("  Solvestatus: " + mi.getSolveStatus());
                 System.out.println("  Obj: " + mi.SyncDB().getVariable("z").findRecord().getLevel());
             }
         }
     }


     static String model =
        "Sets                                                                    \n" +
        "      i   canning plants   / seattle, san-diego /                       \n" +
        "      j   markets          / new-york, chicago, topeka / ;              \n" +
        "                                                                        \n" +
        "Parameters                                                              \n" +
        "    a(i)  capacity of plant i in cases                                  \n" +
        "           /    seattle     350                                         \n" +
        "                san-diego   600  /                                      \n" +
        "                                                                        \n" +
        "    b(j)  demand at market j in cases                                   \n" +
        "           /    new-york    325                                         \n" +
        "                chicago     300                                         \n" +
        "                topeka      275  / ;                                    \n" +
        "                                                                        \n" +
        "Table d(i,j)  distance in thousands of miles                            \n" +
        "             new-york       chicago      topeka                         \n" +
        "seattle        2.5           1.7          1.8                           \n" +
        "san-diego      2.5           1.8          1.4  ;                        \n" +
        "                                                                        \n" +
        "Scalar f      freight in dollars per case per thousand miles  /90/ ;    \n" +
        "Scalar bmult  demand multiplier /1/;                                    \n" +
        "                                                                        \n" +
        "Parameter c(i,j)  transport cost in thousands of dollars per case ;     \n" +
        "          c(i,j) = f * d(i,j) / 1000 ;                                  \n" +
        "                                                                        \n" +
        "Variables                                                               \n" +
        "    x(i,j)  shipment quantities in cases                                \n" +
        "    z       total transportation costs in thousands of dollars ;        \n" +
        "                                                                        \n" +
        "Positive Variable x ;                                                   \n" +
        "                                                                        \n" +
        "Equations                                                               \n" +
        "      cost        define objective function                             \n" +
        "      supply(i)   observe supply limit at plant i                       \n" +
        "      demand(j)   satisfy demand at market j ;                          \n" +
        "                                                                        \n" +
        "  cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                    \n" +
        "                                                                        \n" +
        "  supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                            \n" +
        "                                                                        \n" +
        "  demand(j) ..   sum(i, x(i,j))  =g=  bmult*b(j) ;                      \n" +
        "                                                                        \n" +
        "Model transport /all/ ;                                                 \n" +
        "                                                                        \n";
}
