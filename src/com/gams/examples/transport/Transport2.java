package com.gams.examples.transport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.gams.api.GAMSGlobals;
import com.gams.api.GAMSVariable;
import com.gams.api.GAMSVariableRecord;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example shows how to include data files to run a job with the simple 
 * GAMS [trnsport] model from the GAMS Model Library.  It corresponds to 
 * '--incname=tdata' running GAMS on the command line.
 */ 
public class Transport2 {

     public static void main(String[] args)  {
         GAMSWorkspace ws = null;
         // check workspace info from command line arguments
         if (args.length > 0) {  
             GAMSWorkspaceInfo wsInfo = new GAMSWorkspaceInfo();
             wsInfo.setSystemDirectory(args[0]);
             // create GAMSWorkspace "ws" with user-specified system directory and the default working directory 
             // (the directory named with current date and time under System.getProperty("java.io.tmpdir"))
             ws = new GAMSWorkspace(wsInfo);
         } else {
             // create GAMSWorkspace "ws" with default system directory and default working directory 
             // (the directory named with current date and time under System.getProperty("java.io.tmpdir"))
             ws = new GAMSWorkspace();
         }
  
         // write "data" into file "tdata.gms" under GAMSWorkspace's working directory
         try {
            BufferedWriter file = new BufferedWriter(new FileWriter(ws.workingDirectory() + GAMSGlobals.FILE_SEPARATOR + "tdata.gms"));
            file.write(data);
            file.close();
         } catch(IOException e) {
             e.printStackTrace();
             System.exit(-1);
         }

         // create GAMSJob "t2" from the "model" string variable
         GAMSJob t2 = ws.addJobFromString(model);
         // create GAMSOption "opt" and define "incname" as "tdata"
         GAMSOptions opt = ws.addOptions();
         opt.defines("incname", "tdata");
         // run GAMSJob "t2" with GAMSOptions "opt"
         t2.run(opt);

        // retrieve GAMSVariable "x" from GAMSJob's output databases
         GAMSVariable var = t2.OutDB().getVariable("x");
         for (GAMSVariableRecord rec : var)
            System.out.println("x(" + rec.getKey(0) + ", " + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());

         // cleanup GAMSWorkspace's working directory
         cleanup(ws.workingDirectory());
         // terminate program
         System.exit(0);
    }

    static void cleanup(String directory)  {
         File directoryToDelete = new File(directory);
         String files[] = directoryToDelete.list();
         for (String file : files) {
            File fileToDelete = new File(directoryToDelete, file);
            try {
                 fileToDelete.delete();
            } catch(Exception e){
                 e.printStackTrace();
            }
         }
         try {
           directoryToDelete.delete();
         } catch(Exception e) {
            e.printStackTrace();
         }
    }


    // data
    static String data =
                   "Sets                                                   \n" +
                   "  i   canning plants   / seattle, san-diego /          \n" +
                   "  j   markets          / new-york, chicago, topeka / ; \n" +
                   "Parameters                                             \n" +
                   "                                                       \n" +
                   "  a(i)  capacity of plant i in cases                   \n" +
                   "                     /    seattle     350              \n" +
                   "                          san-diego   600  /           \n" +
                   "                                                       \n" +
                   "  b(j)  demand at market j in cases                    \n" +
                   "                     /    new-york    325              \n" +
                   "                          chicago     300              \n" +
                   "                          topeka      275  / ;         \n" +
                   "                                                       \n" +
                   "Table d(i,j)  distance in thousands of miles           \n" +
                   "               new-york       chicago      topeka      \n" +
                   "  seattle          2.5           1.7          1.8      \n" +
                   "  san-diego        2.5           1.8          1.4  ;   \n" +
                   "                                                       \n" +
                   "Scalar f  freight in dollars per case per thousand miles  /90/ \n " +
                   "                                                               \n";

    // model
    static String model =
        "Sets                                                                    \n" +
        "      i   canning plants                                                \n" +
        "      j   markets                                                       \n" +
        "                                                                        \n" +
        "Parameters                                                              \n" +
        "      a(i)   capacity of plant i in cases                               \n" +
        "      b(j)   demand at market j in cases                                \n" +
        "      d(i,j) distance in thousands of miles                             \n" +
        "Scalar f  freight in dollars per case per thousand miles;               \n" +
        "                                                                        \n" +
        "$if not set incname $abort 'no include file name for data file provided'\n" +
        "$include %incname%                                                      \n" +
        "                                                                        \n" +
        " Parameter c(i,j)  transport cost in thousands of dollars per case ;    \n" +
        "                                                                        \n" +
        "            c(i,j) = f * d(i,j) / 1000 ;                                \n" +
        "                                                                        \n" +
        " Variables                                                              \n" +
        "       x(i,j)  shipment quantities in cases                             \n" +
        "       z       total transportation costs in thousands of dollars ;     \n" +
        "                                                                        \n" +
        " Positive Variable x ;                                                  \n" +
        "                                                                        \n" +
        " Equations                                                              \n" +
        "                                                                        \n" +
        "      cost        define objective function                             \n" +
        "      supply(i)   observe supply limit at plant i                       \n" +
        "       demand(j)   satisfy demand at market j ;                         \n" +
        "                                                                        \n" +
        "  cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                    \n" +
        "                                                                        \n" +
        "  supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                            \n" +
        "                                                                        \n" +
        "  demand(j) ..   sum(i, x(i,j))  =g=  b(j) ;                            \n" +
        "                                                                        \n" +
        " Model transport /all/ ;                                                \n" +
        "                                                                        \n" +
        " Solve transport using lp minimizing z ;                                \n" +
        "                                                                        \n" +
        "Display x.l, x.m ;                                                      \n" +
        "                                                                        \n";

}

