package com.gams.examples.transport;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.gams.api.GAMSCheckpoint;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSModelInstance;
import com.gams.api.GAMSModifier;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example shows how to use a queue to solve multiple  GAMSModelInstances 
 * in parallel. The example runs the jobs with the GAMS [trnsport] model from 
 * the GAMS Model Library. 
 */
public class Transport8 {

    public static void main(String[] args) {
        // check workspace info from command line arguments
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0)
            wsInfo.setSystemDirectory( args[0] );
        // create a directory
        File workingDirectory = new File(System.getProperty("user.dir"), "Transport8");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        // create a workspace
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);
        // create a checkpoint
        GAMSCheckpoint cp = ws.addCheckpoint();

        // initialize a checkpoint by running a job
        GAMSJob t8 = ws.addJobFromString(model);
        t8.run(cp);

        Queue<Double> bmultQueue = new LinkedList<Double>(
             Arrays.asList( Double.valueOf(0.6), Double.valueOf(0.7), Double.valueOf(0.8), Double.valueOf(0.9),
                            Double.valueOf(1.0), Double.valueOf(1.1), Double.valueOf(1.2), Double.valueOf(1.3) )
        );

        // solve multiple model instances in parallel
        Object IOLockObject = new Object();
        int numberOfWorkers = 2;
        Scenarios[] scenarios = new Scenarios[numberOfWorkers];
        for (int i=0; i<numberOfWorkers; i++) {
             scenarios[i] = new Scenarios( ws, cp, bmultQueue, IOLockObject, i );
            scenarios[i].start();
        }
        for (int i=0; i<numberOfWorkers; i++) {
            try {
                 scenarios[i].join();
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }

   /** A scenario thread to run a GAMSModelInstance. */
    static class Scenarios extends Thread {
        GAMSWorkspace workspace;
        GAMSCheckpoint checkpoint;
        Object IOLockObject;
        Queue<Double> bmultQueue;
        int workerNumber;

        /** Scenearios constructor
        * @param   ws   a workspace where the files are located
        * @param   cp   a checkpoint
        * @param   que  a queue of demaind multiplier
        * @param   IOLockObj  an IO synchronization object
        * @param   i    scenario number
        */
        public Scenarios(GAMSWorkspace ws, GAMSCheckpoint cp, Queue<Double> que, Object IOLockObj, int i) {
              workspace = ws;
              checkpoint = cp;
              IOLockObject = IOLockObj;
              bmultQueue = que;
              workerNumber = i;
        }

        /** Instantiate and solve a GAMSModelInstance. */
        public void run() {     
              GAMSModelInstance mi = null;
              synchronized (bmultQueue) {
                  mi = checkpoint.addModelInstance();
              }

              GAMSParameter bmult = mi.SyncDB().addParameter("bmult", "demand multiplier");

              GAMSOptions opt = workspace.addOptions();
              opt.setAllModelTypes("cplexd");

              // instantiate the GAMSModelInstance and pass a model definition and GAMSModifier to declare bmult mutable
              mi.instantiate("transport use lp min z", opt, new GAMSModifier(bmult));

              bmult.addRecord().setValue( 1.0 );

              while (true) {
                  double b = 0.0;
                  // dynamically get a bmult value from the queue instead of passing it to the different threads at creation time
                  synchronized (bmultQueue) {
                       if (bmultQueue.isEmpty())
                           break;
                       else
                         b = bmultQueue.remove();
                   }
                  bmult.getFirstRecord().setValue(b);
                  mi.solve();
                  // we need to make the output a critical section to avoid messed up report informations
                  synchronized (IOLockObject) {
                      System.out.println("#"+workerNumber+":Scenario bmult=" + b + ":");
                      System.out.println("     Modelstatus: " + mi.getModelStatus());
                      System.out.println("     Solvestatus: " + mi.getSolveStatus());
                      System.out.println("     Obj: " + mi.SyncDB().getVariable("z").findRecord().getLevel());
                  }
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
