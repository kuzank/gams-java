package com.gams.examples.transport;

import java.io.File;

import com.gams.api.GAMSCheckpoint;
import com.gams.api.GAMSGlobals;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example shows how to initialize a GAMSJob from a GAMSCheckpoint. 
 * The example runs the job with the GAMS [trnsport] model from the GAMS 
 * Model Library. 
 */
public class Transport5 {

   public static void main(String[] args) {
       // check workspace info from command line arguments
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0)
            wsInfo.setSystemDirectory( args[0] );
        // create a directory
        File workingDirectory = new File(System.getProperty("user.dir"), "Transport5");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        // create a workspace
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);
        // create a checkpoint
        GAMSCheckpoint cp = ws.addCheckpoint();

        // initialize a checkpoint by running a job
        GAMSJob t5 = ws.addJobFromString(model);
        t5.run(cp);

        double[] bmultlist = new double[] { 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3 };

        // create a new GAMSJob that is initialized from the GAMSCheckpoint
        for(double b : bmultlist)
        {
            t5 = ws.addJobFromString("bmult=" + b + "; solve transport min z use lp; ms=transport.modelstat; ss=transport.solvestat;", cp);
            t5.run();

            System.out.println("Scenario bmult=" + b + ":");
            System.out.println("  Modelstatus: " +  GAMSGlobals.ModelStat.lookup( (int) t5.OutDB().getParameter("ms").findRecord().getValue() ));
            System.out.println("  Solvestatus: " +  GAMSGlobals.SolveStat.lookup( (int)t5.OutDB().getParameter("ss").findRecord().getValue() ));
            System.out.println("  Obj: " + t5.OutDB().getVariable("z").findRecord().getLevel());
        }

    }

    static String model =
          " Sets                                                               \n"+
          "     i   canning plants   / seattle, san-diego /                    \n"+
          "     j   markets          / new-york, chicago, topeka / ;           \n"+
          "                                                                    \n"+
          "Parameters                                                          \n"+
          "\n"+
          "     a(i)  capacity of plant i in cases                             \n"+
          "       /    seattle     350                                         \n"+
          "            san-diego   600  /                                      \n"+
          "                                                                    \n"+
          "     b(j)  demand at market j in cases                              \n"+
          "       /    new-york    325                                         \n"+
          "            chicago     300                                         \n"+
          "            topeka      275  / ;                                    \n"+
          "                                                                    \n"+
          "Table d(i,j)  distance in thousands of miles                        \n"+
          "                  new-york       chicago      topeka                \n"+
          "    seattle          2.5           1.7          1.8                 \n"+
          "    san-diego        2.5           1.8          1.4  ;              \n"+
          "                                                                    \n"+
          "Scalar f      freight in dollars per case per thousand miles  /90/ ;\n"+
          "Scalar bmult  demand multiplier /1/;                                \n"+
          "                                                                    \n"+
          "Parameter c(i,j)  transport cost in thousands of dollars per case ; \n"+
          "                                                                    \n"+
          "          c(i,j) = f * d(i,j) / 1000 ;                              \n"+
          "                                                                    \n"+
          "Variables                                                           \n"+
          "     x(i,j)  shipment quantities in cases                           \n"+
          "     z       total transportation costs in thousands of dollars ;   \n"+
          "                                                                    \n"+
          "Positive Variable x ;                                               \n"+
          "                                                                    \n"+
          "Equations                                                           \n"+
          "     cost        define objective function                          \n"+
          "     supply(i)   observe supply limit at plant i                    \n"+
          "     demand(j)   satisfy demand at market j ;                       \n"+
          "                                                                    \n"+
          "cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                  \n"+
          "                                                                    \n"+
          "supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                          \n"+
          "                                                                    \n"+
          "demand(j) ..   sum(i, x(i,j))  =g=  bmult*b(j) ;                    \n"+
          "                                                                    \n"+
          "Model transport /all/ ;                                             \n"+
          "Scalar ms 'model status', ss 'solve status';                        \n";

}
