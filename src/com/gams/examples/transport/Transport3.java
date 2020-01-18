package com.gams.examples.transport;

import java.io.File;

import com.gams.api.GAMSGlobals;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSVariable;
import com.gams.api.GAMSVariableRecord;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example shows how to set a non-default working directory, to read data 
 * from string, to export datbase to gdx, as well as to run a job using data 
 * from gdx. The example runs a job with the GAMS [trnsport] model from the GAMS 
 * Model Library. 
 */
public class Transport3 {

    public static void main(String[] args)  {
        // check workspace info from command line arguments
        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0)
            wsInfo.setSystemDirectory( args[0] );
        // create a directory
        File workingDirectory = new File(System.getProperty("user.dir"), "Transport3");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        // create a workspace
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);

        // Create and run a job from a data file, then explicitly export to a GDX file
        GAMSJob t3 = ws.addJobFromString(data);
        t3.run();
        t3.OutDB().export( ws.workingDirectory() + GAMSGlobals.FILE_SEPARATOR + "tdata.gdx" );

        // run a job using an instance of GAMSOptions that defines the data include file
        t3 = ws.addJobFromString(model);

        GAMSOptions opt = ws.addOptions();
        opt.defines("gdxincname", "tdata");
        t3.run(opt);

        GAMSVariable x = t3.OutDB().getVariable("x");
        for (GAMSVariableRecord rec : x)
            System.out.println("x(" + rec.getKey(0) + ", " + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());
        System.out.println();

        // similar to the previous run but without exporting database into a file
        GAMSJob t3a = ws.addJobFromString(data);
        GAMSJob t3b = ws.addJobFromString(model);
        opt = ws.addOptions();

        t3a.run();

        opt.defines("gdxincname", t3a.OutDB().getName());
        t3b.run(opt, t3a.OutDB());

        for(GAMSVariableRecord rec : t3b.OutDB().getVariable("x"))
            System.out.println("x(" + rec.getKey(0) + ", " + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());

    }

    static String data =
               "Sets                                                             \n" +
               "  i   canning plants   / seattle, san-diego /                    \n" +
               "  j   markets          / new-york, chicago, topeka / ;           \n" +
               "Parameters                                                       \n" +
               "                                                                 \n" +
               "  a(i)  capacity of plant i in cases                             \n" +
               "                     /    seattle     350                        \n" +
               "                          san-diego   600  /                     \n" +
               "                                                                 \n" +
               "  b(j)  demand at market j in cases                              \n" +
               "                     /    new-york    325                        \n" +
               "                          chicago     300                        \n" +
               "                          topeka      275  / ;                   \n" +
               "                                                                 \n" +
               "Table d(i,j)  distance in thousands of miles                     \n" +
               "               new-york       chicago      topeka                \n" +
               "  seattle          2.5           1.7          1.8                \n" +
               "  san-diego        2.5           1.8          1.4  ;             \n" +
               "                                                                 \n" +
               "Scalar f  freight in dollars per case per thousand miles  /90/ ; \n ";

     static String model = "Sets                                                           \n" +
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
