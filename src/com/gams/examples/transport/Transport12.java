package com.gams.examples.transport;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gams.api.GAMSCheckpoint;
import com.gams.api.GAMSDatabase;
import com.gams.api.GAMSEquation;
import com.gams.api.GAMSEquationRecord;
import com.gams.api.GAMSException;
import com.gams.api.GAMSGlobals;
import com.gams.api.GAMSJob;
import com.gams.api.GAMSModelInstance;
import com.gams.api.GAMSModelInstanceOpt;
import com.gams.api.GAMSModifier;
import com.gams.api.GAMSOptions;
import com.gams.api.GAMSParameter;
import com.gams.api.GAMSParameterRecord;
import com.gams.api.GAMSSet;
import com.gams.api.GAMSSetRecord;
import com.gams.api.GAMSSymbol;
import com.gams.api.GAMSVariable;
import com.gams.api.GAMSVariableRecord;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;


public class Transport12 
{
     public static void main(String[] args) 
     {
         // check workspace info from command line arguments
         GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
         if (args.length > 0)
             wsInfo.setSystemDirectory( args[0] );
         // create a directory
         File workingDirectory = new File(System.getProperty("user.dir"), "Transport12");
         workingDirectory.mkdir();
         wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
         // create a workspace
         GAMSWorkspace ws = new GAMSWorkspace(wsInfo);

         // initialize a checkpont by running a job
         GAMSCheckpoint cp = ws.addCheckpoint();

         GAMSJob t12 = ws.addJobFromString(model);
         t12.run(cp);

         // create a ModelInstance and solve it multiple times with different scalar bmult
         GAMSModelInstance mi = cp.addModelInstance();

         double[] bmultlist = new double[] { 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3 };

         GAMSDatabase db1 = ws.addDatabase();

         GAMSSet scen1 = db1.addSet("scen", 1, "");
         GAMSParameter bmult = db1.addParameter("bmultlist", "", scen1);
         GAMSParameter zscen1 = db1.addParameter("zscen", "", scen1);

         int i = 0;
         for (double b : bmultlist)
         {
             bmult.addRecord("s" + i).setValue(b);
             scen1.addRecord("s" + i);
                i++;
         }

         GAMSSet dict = db1.addSet("dict",3,"");
         dict.addRecord( new String[]{ scen1.getName(), "scenario", "" } );
         dict.addRecord( new String[]{"bmult", "param", bmult.getName() } );
         dict.addRecord( new String[]{"z", "level", zscen1.getName()} );

         GUSSCall(dict, mi, "transport use lp min z", null, null, null); //System.out);

         for (GAMSParameterRecord rec : db1.getParameter(zscen1.getName()))
              System.out.println(rec.getKey(0) + " obj: " + rec.getValue());

         //*******************

         GAMSModelInstance mi2 = cp.addModelInstance();
         GAMSDatabase db2 = ws.addDatabase();

         GAMSSet scen2 = db2.addSet("scen", 1, "");
         GAMSParameter zscen2 = db2.addParameter("zscen", "", scen2);
         GAMSParameter xup = db2.addParameter("xup", 3, "");

         for (int j = 0; j < 4; j++) {
              for (GAMSSetRecord irec : t12.OutDB().getSet("i"))
                  for (GAMSSetRecord jrec : t12.OutDB().getSet("j")) 
                  {
                     String[] keys = new String[] { "s" + j, irec.getKey(0), jrec.getKey(0) };
                     xup.addRecord(keys).setValue(j+1);
                  }
              scen2.addRecord("s" + j);
         }

         GAMSSet dict2 = db2.addSet("dict", 3, "");
         dict2.addRecord( new String[] {scen2.getName(), "scenario", ""} );
         dict2.addRecord( new String[] {"x", "lower", xup.getName()} );
         dict2.addRecord( new String[] {"z", "level", zscen2.getName()} );

         GUSSCall(dict2, mi2, "transport use lp min z", null, null, System.out);

         for (GAMSParameterRecord rec : db2.getParameter(zscen2.getName()))
             System.out.println(rec.getKey(0) + " obj: " + rec.getValue());
     }

     // Needs to be called with an uninstantiated GAMSModelInstance
     static void GUSSCall(GAMSSet dict, GAMSModelInstance mi, String solveStatement, GAMSOptions opt, GAMSModelInstanceOpt miOpt, PrintStream output) 
     {
         List<Object[]> modifierList = Collections.synchronizedList( new ArrayList<Object[]>() );

         if (dict.getDimension() != 3)
             throw new GAMSException("Dict needs to be 3-dimensional");

         String scenName = dict.getFirstRecord(new String[] { " ", "scenario", " " }).getKey(0);
         GAMSSet scenSymbol = dict.getDatabase().getSet(scenName);

         for (GAMSSetRecord rec : dict)
         {

             if (rec.getKey(1).toLowerCase().equals("scenario"))
                 continue;
             if (rec.getKey(1).toLowerCase().equals("param"))
             {
                 int modifierDim = dict.getDatabase().getParameter(rec.getKey(2)).getDimension() - scenSymbol.getDimension();
                 if (modifierDim < 0)
                     throw new GAMSException("Dimension of " + rec.getKey(2) + " too small");
                 GAMSModifier mod = new GAMSModifier(mi.SyncDB().addParameter(rec.getKey(0), modifierDim, ""));
                 GAMSParameter param = dict.getDatabase().getParameter( rec.getKey(2)); 
                 modifierList.add(
                     new Object[] { mod, param }
                 );
             }
             else  if ((rec.getKey(1).toLowerCase().equals("lower")) ||
                      (rec.getKey(1).toLowerCase().equals("upper")) ||
                      (rec.getKey(1).toLowerCase().equals("fixed")))
                   {
                       int modifierDim = (int) (dict.getDatabase().getParameter(rec.getKey(2)).getDimension() - scenSymbol.getDimension());
                       if (modifierDim < 0)
                          throw new GAMSException("Dimension of " + rec.getKey(2) + " too small");
                       GAMSVariable modifierVar = null;
                       try  {
                          modifierVar = dict.getDatabase().getVariable(rec.getKey(0));
                       }
                       catch (Exception e) {
                           modifierVar = mi.SyncDB().addVariable(rec.getKey(0),modifierDim, GAMSGlobals.VarType.FREE, "");
                       }
                       if (rec.getKey(1).toLowerCase().equals("lower"))
                       {
                           GAMSModifier mod = new GAMSModifier(modifierVar, GAMSGlobals.UpdateAction.LOWER, mi.SyncDB().addParameter(rec.getKey(2), modifierDim, ""));
                           GAMSParameter param = dict.getDatabase().getParameter(rec.getKey(2));
                           modifierList.add(
                               new Object[] { mod , param }
                           );
                       }
                       else if (rec.getKey(1).toLowerCase().equals("upper"))
                            {
                              GAMSModifier mod = new GAMSModifier(modifierVar, GAMSGlobals.UpdateAction.UPPER, mi.SyncDB().addParameter(rec.getKey(2), modifierDim, ""));
                              GAMSParameter param = dict.getDatabase().getParameter(rec.getKey(2));
                              modifierList.add(
                                 new Object[] { mod , param }
                              );
                           }
                           else  { // fixed
                                    GAMSModifier mod = new GAMSModifier(modifierVar, GAMSGlobals.UpdateAction.FIXED, mi.SyncDB().addParameter(rec.getKey(2), modifierDim, ""));
                                    GAMSParameter param = dict.getDatabase().getParameter(rec.getKey(2));
                                    modifierList.add(
                                       new Object[] { mod , param }
                                    );
                                 }
                   }
                   else if ((rec.getKey(1).toLowerCase().equals("level")) || (rec.getKey(1).toLowerCase().equals( "marginal")))
                        {
                           // Check that parameter exists in GAMSDatabase, will throw an exception if not
                           @SuppressWarnings("unused")
                           GAMSParameter x = dict.getDatabase().getParameter(rec.getKey(2));
                        }
                        else
                            throw new GAMSException("Cannot handle UpdateAction " + rec.getKey(1));
         }

         List<GAMSModifier> mL = Collections.synchronizedList( new ArrayList<GAMSModifier>() );
         for (Object[] tuple : modifierList) {
            if (tuple[0] instanceof GAMSModifier)
                mL.add( (GAMSModifier) tuple[0] );
         }
         GAMSModifier[] mods = (GAMSModifier[]) mL.toArray(new GAMSModifier[mL.size()]);
         
         mi.instantiate(solveStatement, opt, mods);

         List<Object[]> outList = Collections.synchronizedList( new ArrayList<Object[]>() );

         for (GAMSSetRecord s : scenSymbol) 
         {
            for (Object[] tuple : modifierList)
            {
                 GAMSParameter p;
                 GAMSParameter pscen = (GAMSParameter) tuple[1];

                 GAMSModifier m = (GAMSModifier) tuple[0];
                 if (m.getDataSymbol() == null)
                     p = (GAMSParameter)m.getGamsSymbol();
                 else 
                     p = m.getDataSymbol();

                 // Implemented SymbolUpdateType=BaseCase
                 p.clear();

                 GAMSParameterRecord rec;
                 String[] filter = new String[pscen.getDimension()];
                 for (int i = 0; i < scenSymbol.getDimension(); i++)
                     filter[i] = s.getKey(i);
                 for (int i = scenSymbol.getDimension(); i < pscen.getDimension(); i++)
                     filter[i] = " ";

                 try  
                 {
                     rec = pscen.getFirstRecord(filter);
                 } catch (GAMSException e) {
                     continue;
                 }
                 do 
                 {   String[] myKeys = new String[p.getDimension()];
                     for (int i = 0; i < p.getDimension(); i++)
                        myKeys[i] = rec.getKey(scenSymbol.getDimension()+i);
                     p.addRecord(myKeys).setValue( rec.getValue() );
                 } while (rec.moveNext());
             }

             mi.solve(GAMSModelInstance.SymbolUpdateType.BASECASE, output, miOpt);
             if (outList.size() == 0)
             {
                 for (GAMSSetRecord rec : dict)
                 {
                     if ((rec.getKey(1).toLowerCase().equals("level")) || (rec.getKey(1).toLowerCase().equals("marginal")))
                     {
                         GAMSSymbol<?> sym = mi.SyncDB().getSymbol(rec.getKey(0));
                         GAMSParameter param = dict.getDatabase().getParameter(rec.getKey(2));
                         String str = rec.getKey(1).toLowerCase();
                         outList.add(
                                 new Object[] { sym, param, str }
                         );
                     }
                 }
             }
             for (Object[] tuple : outList)
             {
                 GAMSSymbol<?> symbol = (GAMSSymbol<?>) tuple[0];
                 GAMSParameter param = (GAMSParameter) tuple[1];
                 String str = (String)tuple[2];
                 String[] myKeys = new String[scenSymbol.getDimension() + symbol.getFirstRecord().getKeys().length];
                 for (int i = 0; i < scenSymbol.getDimension(); i++)
                     myKeys[i] = s.getKey(i);

                 if ((str.equals("level")) && (symbol instanceof GAMSVariable))
                 {
                     GAMSVariable var = (GAMSVariable) symbol;
                     for (GAMSVariableRecord rec : var)
                     {
                         for (int i = 0; i < rec.getKeys().length; i++)
                             myKeys[scenSymbol.getDimension() + i] = s.getKey(i);
                         param.addRecord(myKeys).setValue( rec.getLevel() );
                     }
                 }
                 else if ((str.equals("level")) && (symbol instanceof GAMSEquation))
                      {
                         GAMSEquation eq = (GAMSEquation) symbol;
                         for (GAMSEquationRecord rec : eq)
                         {
                             for (int i = 0; i < rec.getKeys().length; i++)
                                 myKeys[scenSymbol.getDimension() + i] = s.getKey(i);
                             param.addRecord(myKeys).setValue( rec.getLevel() );
                         }
                      }
                      else if ((str.equals("marginal")) && (symbol instanceof GAMSVariable))
                           {
                              GAMSVariable var = (GAMSVariable) symbol;
                              for (GAMSVariableRecord rec : var)
                              {
                                 for (int i = 0; i < rec.getKeys().length; i++)
                                     myKeys[scenSymbol.getDimension() + i] = s.getKey(i);
                                 param.addRecord(myKeys).setValue( rec.getMarginal() );
                              }
                           }
                           else if ((str.equals("marginal")) && (symbol instanceof GAMSEquation))
                                {
                                    GAMSEquation eq = (GAMSEquation) symbol;
                                    for (GAMSEquationRecord rec : eq)
                                    {
                                       for (int i = 0; i < rec.getKeys().length; i++)
                                           myKeys[scenSymbol.getDimension() + i] = s.getKey(i);
                                       param.addRecord(myKeys).setValue( rec.getMarginal() );
                                    }
                                }
             }
         }
     }

    static String model =
          "Sets                                                                \n" +
          "     i   canning plants   / seattle, san-diego /                    \n" +
          "     j   markets          / new-york, chicago, topeka / ;           \n" +
          "                                                                    \n" +
          "Parameters                                                          \n" +
          "                                                                    \n" +
          "     a(i)  capacity of plant i in cases                             \n" +
          "       /    seattle     350                                         \n" +
          "            san-diego   600  /                                      \n" +
          "                                                                    \n" +
          "     b(j)  demand at market j in cases                              \n" +
          "       /    new-york    325                                         \n" +
          "            chicago     300                                         \n" +
          "            topeka      275  / ;                                    \n" +
          "                                                                    \n" +
          "Table d(i,j)  distance in thousands of miles                        \n" +
          "                  new-york       chicago      topeka                \n" +
          "    seattle          2.5           1.7          1.8                 \n" +
          "    san-diego        2.5           1.8          1.4  ;              \n" +
          "                                                                    \n" +
          "Scalar f      freight in dollars per case per thousand miles  /90/ ;\n" +
          "Scalar bmult  demand multiplier /1/;                                \n" +
          "                                                                    \n" +
          "Parameter c(i,j)  transport cost in thousands of dollars per case ; \n" +
          "                                                                    \n" +
          "          c(i,j) = f * d(i,j) / 1000 ;                              \n" +
          "                                                                    \n" +
          "Variables                                                           \n" +
          "     x(i,j)  shipment quantities in cases                           \n" +
          "     z       total transportation costs in thousands of dollars ;   \n" +
          "                                                                    \n" +
          "Positive Variable x ;                                               \n" +
          "                                                                    \n" +
          "Equations                                                           \n" +
          "     cost        define objective function                          \n" +
          "     supply(i)   observe supply limit at plant i                    \n" +
          "     demand(j)   satisfy demand at market j ;                       \n" +
          "                                                                    \n" +
          "cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                  \n" +
          "                                                                    \n" +
          "supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                          \n" +
          "                                                                    \n" +
          "demand(j) ..   sum(i, x(i,j))  =g=  bmult*b(j) ;                    \n" +
          "                                                                    \n" +
          "Model transport /all/ ;                                             \n" +
          "                                                                    \n";

}
