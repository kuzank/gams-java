/*  Java code generated by apiwrapper for GAMS Version 30.1.0
 *
 * GAMS - Loading mechanism for GAMS Expert-Level APIs
 *
 * Copyright (c) 2016-2020 GAMS Software GmbH <support@gams.com>
 * Copyright (c) 2016-2020 GAMS Development Corp. <support@gams.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gams.api;

import java.io.File;

public class opt {
   public static final int optDataNone    = 0; /* optDataType */
   public static final int optDataInteger = 1;
   public static final int optDataDouble  = 2;
   public static final int optDataString  = 3;
   public static final int optDataStrList = 4;

   public static final int optTypeInteger   = 0; /* optOptionType */
   public static final int optTypeDouble    = 1;
   public static final int optTypeString    = 2;
   public static final int optTypeBoolean   = 3;
   public static final int optTypeEnumStr   = 4;
   public static final int optTypeEnumInt   = 5;
   public static final int optTypeMultiList = 6;
   public static final int optTypeStrList   = 7;
   public static final int optTypeMacro     = 8;
   public static final int optTypeImmediate = 9;

   public static final int optsubRequired = 0; /* optOptionSubType */
   public static final int optsubNoValue  = 1;
   public static final int optsubOptional = 2;
   public static final int optsub2Values  = 3;

   public static final int optMsgInputEcho    = 0; /* optMsgType */
   public static final int optMsgHelp         = 1;
   public static final int optMsgDefineError  = 2;
   public static final int optMsgValueError   = 3;
   public static final int optMsgValueWarning = 4;
   public static final int optMsgDeprecated   = 5;
   public static final int optMsgFileEnter    = 6;
   public static final int optMsgFileLeave    = 7;
   public static final int optMsgTooManyMsgs  = 8;
   public static final int optMsgUserError    = 9;

   public static final int optMapIndicator  = 0; /* optVarEquMapType */
   public static final int optMapDefinedVar = 1;

   private long optPtr = 0;
   public native static int    GetReady (String[] msg);
   public native static int    GetReadyD(String dirName, String[] msg);
   public native static int    GetReadyL(String libName, String[] msg);
   public native int    Create   (String[] msg);
   public native int    CreateD  (String dirName, String[] msg);
   public native int    CreateL  (String libName, String[] msg);
   public native int    Free     ();
   public native int    ReadDefinition(String fn);
   public native int    ReadDefinitionFromPChar(String []p);
   public native int    ReadParameterFile(String fn);
   public native void    ReadFromStr(String s);
   public native int    WriteParameterFile(String fn);
   public native void    ClearMessages();
   public native void    AddMessage(String info);
   public native void    GetMessage(int NrMsg, String []info, int []iType);
   public native void    ResetAll();
   public native void    ResetAllRecent();
   public native void    ResetRecentChanges();
   public native void    ShowHelp(String AHlpID);
   public native int    ResetNr(int ANr);
   public native int    FindStr(String AName, int []ANr, int []ARefNr);
   public native int    GetInfoNr(int ANr, int []ADefined, int []ADefinedR, int []ARefNr, int []ADataType, int []AOptType, int []ASubType);
   public native int    GetValuesNr(int ANr, String []ASName, int []AIVal, double []ADVal, String []ASVal);
   public native int    SetValuesNr(int ANr, int AIVal, double ADVal, String ASVal);
   public native int    SetValues2Nr(int ANr, int AIVal, double ADVal, String ASVal);
   public native void    Version(String []sversion);
   public native void    DefinitionFile(String []sfilename);
   public native int    GetFromAnyStrList(int idash, String []skey, String []sval);
   public native int    GetFromListStr(String skey, String []sval);
   public native int    ListCountStr(String skey);
   public native int    ReadFromListStr(String skey, int iPos, String []sval);
   public native int    SynonymCount();
   public native int    GetSynonym(int NrSyn, String []SSyn, String []SName);
   public native void    EchoSet(int AIVal);
   public native int    EOLOnlySet(int AIVal);
   public native void    NoBoundsSet(int AIVal);
   public native int    EOLChars(String []EOLChars);
   public native void    ErrorCount(int []iErrors, int []iWarnings);
   public native int    GetBoundsInt(int ANr, int []ilval, int []ihval, int []idval);
   public native int    GetBoundsDbl(int ANr, double []dlval, double []dhval, double []ddval);
   public native int    GetDefaultStr(int ANr, String []sval);
   public native int    GetIntNr(int ANr, int []AIVal);
   public native int    GetInt2Nr(int ANr, int []AIVal);
   public native int    SetIntNr(int ANr, int AIVal);
   public native int    SetInt2Nr(int ANr, int AIVal);
   public native int    GetStrNr(int ANr, String []ASVal);
   public native int    GetOptHelpNr(int ANr, String []AName, int []AHc, int []AGroup);
   public native int    GetEnumHelp(int ANr, int AOrd, int []AHc, String []AHelpStr);
   public native int    GetEnumStrNr(int ANr, String []ASVal, int []AOrd);
   public native int    GetEnumCount(int ANr, int []ACount);
   public native int    GetEnumValue(int ANr, int AOrd, int []AValInt, String []AValStr);
   public native int    GetStr2Nr(int ANr, String []ASVal);
   public native int    SetStrNr(int ANr, String ASVal);
   public native int    SetStr2Nr(int ANr, String ASVal);
   public native int    GetDblNr(int ANr, double []ADVal);
   public native int    GetDbl2Nr(int ANr, double []ADVal);
   public native int    SetDblNr(int ANr, double ADVal);
   public native int    SetDbl2Nr(int ANr, double ADVal);
   public native int    GetValStr(String AName, String []ASVal);
   public native int    GetVal2Str(String AName, String []ASVal);
   public native int    GetNameNr(int ANr, String []ASName);
   public native int    GetDefinedNr(int ANr, int []AIVal);
   public native int    GetHelpNr(int ANr, String []ASOpt, String []ASHelp);
   public native int    GetGroupNr(int ANr, String []AName, int []AGroup, int []AHc, String []AHelp);
   public native int    GetGroupGrpNr(int AGroup);
   public native int    GetOptGroupNr(int ANr);
   public native int    GetDotOptNr(int ANr, String []VEName, int []AObjNr, int []ADim, double []AValue);
   public native int    GetDotOptUel(int ANr, int ADim, String []AUEL);
   public native int    GetVarEquMapNr(int maptype, int ANr, String []EquName, String []VarName, int []EquDim, int []VarDim, int []AValue);
   public native int    GetEquVarEquMapNr(int maptype, int ANr, int ADim, String []AIndex);
   public native int    GetVarVarEquMapNr(int maptype, int ANr, int ADim, String []AIndex);
   public native int    VarEquMapCount(int maptype, int []ANrErrors);
   public native int    GetIndicatorNr(int ANr, String []EquName, String []VarName, int []EquDim, int []VarDim, int []AValue);
   public native int    GetEquIndicatorNr(int ANr, int ADim, String []AIndex);
   public native int    GetVarIndicatorNr(int ANr, int ADim, String []AIndex);
   public native int    IndicatorCount(int []ANrErrors);
   public native int    DotOptCount(int []ANrErrors);
   public native int    SetRefNr(int ANr, int ARefNr);
   public native int    SetRefNrStr(String AOpt, int ARefNr);
   public native int    GetConstName(int cgroup, int cindex, String []cname);
   public native int    GetTypeName(int TNr, String []sTName);
   public native int    LookUp(String AOpt);
   public native void    ReadFromPChar(String []p);
   public native void    ReadFromCmdLine(String []p);
   public native void    ReadFromCmdArgs(String cb);
   public native int    GetNameOpt(String ASVal, String []solver, int []opt);
   public native boolean    ResetStr(String AName);
   public native boolean    GetDefinedStr(String AName);
   public native int    GetIntStr(String AName);
   public native double    GetDblStr(String AName);
   public native void    SetIntStr(String AName, int AIVal);
   public native void    SetDblStr(String AName, double ADVal);
   public native void    SetStrStr(String AName, String ASVal);
   public native boolean    IsDeprecated(String AName);
   public native int    Count();
   public native int    MessageCount();
   public native int    GroupCount();
   public native int    RecentEnabled();
   public native void    RecentEnabledSet(int x);
   private native String    GetStrStr(String AName, String []sst_result);
   public String    GetStrStr(String AName) {
       String[] sst_result = new String[1];
       return    GetStrStr(AName, sst_result);
   }
   private native String    Separator(String []sst_result);
   public  String    Separator() {
       String[] sst_result = new String[1];
       return    Separator(sst_result);
   }
   private native String    StringQuote(String []sst_result);
   public  String    StringQuote() {
       String[] sst_result = new String[1];
       return    StringQuote(sst_result);
   }
   public        long    GetoptPtr(){ return optPtr;}
   public opt () { }
   public opt (long optPtr) {
      this.optPtr = optPtr;
   }
   static {
      String stem = "optjni";
      String bitsuffix = "";
 
      if ( System.getProperty("os.arch").toLowerCase().indexOf("64") >= 0 ||
           System.getProperty("os.arch").toLowerCase().indexOf("sparcv9") >= 0 ) {
           bitsuffix = "64";
      }
 
      String os = System.getProperty("os.name").toLowerCase();
      String prefix = "";
      String suffix = "";
 
      if (os.indexOf("win") >=0) {
         suffix = ".dll";
      } else if (os.indexOf("mac") >= 0) {
          prefix = "lib";
          suffix = ".dylib";
      } else {
          prefix = "lib";
          suffix = ".so";
      }
 
      boolean loaded = false;
      try  {
           String libPath = System.getProperty("java.library.path");
           if (libPath != null) {
               for (String str : libPath.split(System.getProperty("path.separator")) ) {
                   File curPath = new File(str);
                   String fileName = curPath.getCanonicalPath() + File.separator + prefix + stem + bitsuffix + suffix;
                   if ( new File(fileName).exists() ) {
                       System.load(fileName);
                       loaded = true;
                       break;
                   }
                }
            }
       } catch (Exception e) {
           loaded = false;
       } catch (UnsatisfiedLinkError e1) {
           loaded = false;
       } finally {
            if (!loaded) {
                try  {
                   System.loadLibrary(stem + bitsuffix);
                } catch (UnsatisfiedLinkError e1) {
                    String libraryFullPath = null;
                    String classPath = null;
                    try {
                       String packageName = (Class.forName(opt.class.getName()).getPackage().getName());
                       StringBuilder sb = new StringBuilder();
                       String[] bs = packageName.split("\\.");
                       for (String s : bs) {
                          sb.append(s);
                          sb.append("/");
                        }
                       sb.append(opt.class.getSimpleName());
                       sb.append(".class");
                       ClassLoader cl = opt.class.getClassLoader();
 
                       classPath = cl.getResource(sb.toString()).getPath();
                       sb.insert(0, "/");
                       classPath = classPath.substring(0, classPath.lastIndexOf(sb.toString()));
                       if (classPath.endsWith("!")) {
                          int index = classPath.lastIndexOf("/");
                          if (index >= 0)
                              classPath = classPath.substring(0, index);
                        }
                       if (classPath.indexOf("/") >= 0) {
                           classPath = classPath.substring(classPath.indexOf(":")+1,classPath.length());
                       }
 
                       libraryFullPath = classPath + "/" + prefix + stem + bitsuffix + suffix ;
                       java.io.File apath = new java.io.File(libraryFullPath);
                       libraryFullPath = java.net.URLDecoder.decode(apath.getAbsolutePath(), "UTF-8");
                    } catch (Exception e2) {
                          e2.printStackTrace();
                          e1.printStackTrace();
                          throw (e1);
                    } finally {
                         if (libraryFullPath == null)  {
                            e1.printStackTrace();
                            throw (e1);
                         }
                    }
 
                    try {
                        System.load(libraryFullPath);
                    } catch (UnsatisfiedLinkError e3) {
                        e3.printStackTrace();
                        throw (e3);
                    }
               }
           }
        }
     }
}
