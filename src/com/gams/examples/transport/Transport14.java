package com.gams.examples.transport;

import java.io.File;

import com.gams.api.GAMSDatabase;
import com.gams.api.GAMSException;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example shows how to run multiple GAMSJobs in parallel each using 
 * different scenario. The example runs the jobs with the GAMS [trnsport] 
 * model from the GAMS Model Library. 
 */
public class Transport14 {
     public static void main(String[] args) {
          initializeWorkspace(args);
          double[] bmultlist = new double[] { 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3 };
          Optimizer[] optim = new Optimizer[bmultlist.length];
          for (int i=0; i<bmultlist.length; i++) {
              optim[i] = new Optimizer(bmultlist[i]);
              optim[i].start();
          }
     }

     static void initializeWorkspace(String[] args) {
          GAMSWorkspaceInfo wsInfo = new GAMSWorkspaceInfo();
          File workingDirectory = new File(System.getProperty("user.dir"), "Transport14");
          workingDirectory.mkdir();
          wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
          if (args.length > 0) 
              wsInfo.setSystemDirectory(args[0]);
          Optimizer.setWorkspace( new GAMSWorkspace(wsInfo) );
     }
}

/** 
 * An optimizer thread to run the model with different demand multipliers. 
 */
class Optimizer extends Thread {
     static GAMSWorkspace workspace = null;
     double bmult = 0.0;

     static void setWorkspace(GAMSWorkspace ws) {
         workspace = ws;
     }

     public Optimizer(double mult) {
         if (workspace == null)
             throw new GAMSException("no workspace information, initialize workspace before creating an Optimizer");
         bmult = mult;
     }

     public void run() {
         GAMSDatabase gDb = workspace.addDatabase();

         GAMSParameter f = gDb.addParameter("f", "freight in dollars per case per thousand miles");
         f.addRecord().setValue( 90 * bmult );

         GAMSJob gModJob = workspace.addJobFromString( model );

         GAMSOptions gOption = workspace.addOptions();
         gOption.defines("gdxincname", gDb.getName());
         gModJob.run(gOption, gDb);

         double obj = gModJob.OutDB().getVariable("z").getFirstRecord().getLevel();
         System.out.println("Scenario bmult=" + bmult + ", Obj=" + obj);
     }

     static String model =
         "Sets                                                                \n"+
         "    i   canning plants   / seattle, san-diego /                     \n"+
         "    j   markets          / new-york, chicago, topeka / ;            \n"+
         "                                                                    \n"+
         "Parameters                                                          \n"+
         "                                                                    \n"+
         "    a(i)  capacity of plant i in cases                              \n"+
         "      /    seattle     350                                          \n"+
         "           san-diego   600  /                                       \n"+
         "                                                                    \n"+
         "    b(j)  demand at market j in cases                               \n"+
         "      /    new-york    325                                          \n"+
         "           chicago     300                                          \n"+
         "           topeka      275  / ;                                     \n"+
         "                                                                    \n"+
         "Table d(i,j)  distance in thousands of miles                        \n"+
         "                 new-york       chicago      topeka                 \n"+
         "   seattle          2.5           1.7          1.8                  \n"+
         "   san-diego        2.5           1.8          1.4  ;               \n"+
         "                                                                   \n"+
         "Scalar f  freight in dollars per case per thousand miles;           \n"+
         "                                                                    \n"+
         "$if not set gdxincname $abort 'no include file name for data file provided'    \n"+
         "$gdxin %gdxincname%                                                 \n"+
         "$load f                                                             \n"+
         "$gdxin                                                              \n"+
         "                                                                    \n"+
         "Parameter c(i,j)  transport cost in thousands of dollars per case ; \n"+
         "                                                                    \n"+
         "         c(i,j) = f * d(i,j) / 1000 ;                               \n"+
         "                                                                    \n"+
         "Variables                                                           \n"+
         "    x(i,j)  shipment quantities in cases                            \n"+
         "    z       total transportation costs in thousands of dollars ;    \n"+
         "                                                                    \n"+
         "Positive Variable x ;                                               \n"+
         "                                                                    \n"+
         "Equations                                                           \n"+
         "    cost        define objective function                           \n"+
         "    supply(i)   observe supply limit at plant i                     \n"+
         "    demand(j)   satisfy demand at market j ;                        \n"+
         "                                                                    \n"+
         "cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                  \n"+
         "                                                                    \n"+
         "supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                          \n"+
         "                                                                    \n"+
         "demand(j) ..   sum(i, x(i,j))  =g=  b(j) ;                          \n"+
         "                                                                    \n"+
         "Model transport /all/ ;                                             \n"+
         "                                                                    \n"+
         "Solve transport using lp minimizing z ;                             \n"+
         "                                                                    \n"+
         "Display x.l, x.m ;                                                  \n"+
         "                                                                    \n";
}
