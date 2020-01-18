package com.gams.examples.transport;

import java.io.PrintStream;

import com.gams.api.GAMSCheckpoint;
import com.gams.api.GAMSDatabase;
import com.gams.api.GAMSException;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSSet;
import com.gams.api.GAMSVariable;
import com.gams.api.GAMSWorkspace;

/**
 * This example shows the wrapper model of a transportation problem based 
 * on the simple GAMS [trnsport] model from the GAMS Model Library. 
 */ 
public class TransportModel 
{
    private GAMSSet fi, fj;
    private GAMSParameter fa, fb, fd, ff;
    private GAMSVariable fx, fz;
    private GAMSOptions fopt;

    private GAMSWorkspace fws;
    private GAMSDatabase fDbIn1, fDbIn2, fDbOut1;

    private GAMSJob job;

    /** TransportModel constructor 
     * @param ws a GAMSWorkspace where the files are located 
     */
    public TransportModel(GAMSWorkspace ws) 
    {
        fws = ws;
        fopt = ws.addOptions();

        fDbIn1 = ws.addDatabase("dbIn1");
        fDbIn2 = ws.addDatabase("dbIn2");

        fopt.defines("dbIn1", "dbIn1");
        fopt.defines("dbIn2", "dbIn2");

        fopt.setSolveLink( GAMSOptions.ESolveLink.LoadLibrary );
        fopt.setAllModelTypes( "Cplex" );
        fopt.defines("dbOut1", "dbOut1");

        fi = fDbIn1.addSet("i", "canning plants");
        fj = fDbIn1.addSet("j", "markets");
        fa = fDbIn1.addParameter("a", "capacity of plant i in cases", fi);
        fb = fDbIn1.addParameter("b", "demand at market j in cases", fj);
        fd = fDbIn1.addParameter("d", "distance in thousands of miles", new Object[] { fi, fj} );
        ff = fDbIn2.addParameter("f", "freight in dollars per case per thousand miles");

        job = ws.addJobFromString( this.getModelSource() );
    }

    /** Executes the trnsport model 
     *  @param   checkpoint  GAMSCheckpoint to be created by GAMSJob
     */
    public void run(GAMSCheckpoint checkpoint) 
    { 
        this.run(checkpoint, null);
    }

    /** Executes the trnsport model 
     *  @param   output  Stream to capture GAMS log
     */
    public void run(PrintStream output) 
    {
        this.run(null, output); 
    }

    /** Executes the trnsport model 
     *  @param   checkpoint  GAMSCheckpoint to be created by GAMSJob
     *  @param   output  Stream to capture GAMS log
     */
    public void run(GAMSCheckpoint checkpoint, PrintStream output) {
        if (!fDbIn1.checkDomains())
            throw new GAMSException("Domain Errors in Database 1");
        if (!fDbIn2.checkDomains())
            throw new GAMSException("Domain Errors in Database 2");
        
        GAMSDatabase[] databases = new GAMSDatabase[] {fDbIn1, fDbIn2 };
        job.run( fopt, checkpoint, output, false, databases );
        
        fDbOut1 = fws.addDatabaseFromGDX(fopt.getDefinitionOf("dbOut1") + ".gdx");
        fx = fDbOut1.getVariable("x");
        fz = fDbOut1.getVariable("z");
    }

    /** i: canning plants */ 
    public GAMSSet geti() { return fi; }

    /** j: markets */ 
    public GAMSSet getj() { return fj; }

    /** a(i): capacity of plant i in cases */
    public GAMSParameter geta() { return fa; }

    /** b(i): demand at market j in cases */ 
    public GAMSParameter getb() { return fb; }

    /** d(i,j): distance in thousands of miles */
    public GAMSParameter getd() { return fd; }

    /** f: freight in dollars per case per thousand miles */
    public GAMSParameter getf() { return ff; }

    /** x(i,j): shipment quantities in cases */
    public GAMSVariable getx()  { return fx; }

    /** z: total transportation costs in thousands of dollars */
    public GAMSVariable getz()  { return fz; }

    /** Options for the execution of the trnsport model */
    public GAMSOptions getopt() { return fopt; }
    
    /** Provide the source of trnsport model */
    public String getModelSource() { return model; }

    private static String model = 
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
            "$if not set dbIn1 $abort 'no file name for in-database 1 file provided'    \n" +
            "$gdxin %dbIn1%                                                             \n" +
            "$load i j a b d                                                            \n" +
            "$gdxin                                                                     \n" +
            "                                                                           \n" +
            "$if not set dbIn2 $abort 'no file name for in-database 2 file provided'    \n" +
            "$gdxin %dbIn2%                                                             \n" +
            "$load f                                                                    \n" +
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
            "$if not set dbOut1 $abort 'no file name for out-database 1 file provided'  \n" +
            "execute_unload '%dbOut1%', x, z;                                           \n" +
            "                                                                           \n";
}
