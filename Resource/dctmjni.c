/*  Java Native Interrface code generated by apiwrapper for GAMS Version 30.1.0
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

#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/* #include <locale.h> */
#include <dctmcc.h>

/* at least for some JNI implementations, JNIEXPORT is not setup to
 * explicitly set visibility to default when using GNU compilers;
 * thus, we do this globally for all functions via this pragma
 *  (which is nicer than redefining JNIEXPORT)
 */
#ifdef __GNUC__
#pragma GCC visibility push(default)
#endif

typedef union foo { void *p; jlong i; } u64_t;

typedef char string255[256];
typedef char stringUEL[GLOBAL_UEL_IDENT_SIZE];


static JavaVM *Cached_JVM = NULL;
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
{
    Cached_JVM = vm;
    dctInitMutexes();
    return JNI_VERSION_1_2;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved)
{
    dctFiniMutexes();
}

/* Prototypes */
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_GetReady(JNIEnv *env, jobject obj, jobjectArray msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_GetReadyD(JNIEnv *env, jobject obj, jstring dirName, jobjectArray msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_GetReadyL(JNIEnv *env, jobject obj, jstring libName, jobjectArray msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_Create(JNIEnv *env, jobject obj, jobjectArray msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_CreateD(JNIEnv *env, jobject obj, jstring dirName, jobjectArray msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_CreateDD(JNIEnv *env, jobject obj, jstring dirName, jobjectArray msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_CreateL(JNIEnv *env, jobject obj, jstring libName, jobjectArray msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_Free(JNIEnv *env, jobject obj);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_LoadEx(JNIEnv *env, jobject obj, jstring fName, jobjectArray Msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_LoadWithHandle(JNIEnv *env, jobject obj, jlong gdxptr, jobjectArray Msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NUels(JNIEnv *env, jobject obj);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_UelIndex(JNIEnv *env, jobject obj, jstring uelLabel);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_UelLabel(JNIEnv *env, jobject obj, jint uelIndex, jbyteArray q, jobjectArray uelLabel);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NLSyms(JNIEnv *env, jobject obj);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymDim(JNIEnv *env, jobject obj, jint symIndex);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymIndex(JNIEnv *env, jobject obj, jstring symName);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymName(JNIEnv *env, jobject obj, jint symIndex, jobjectArray symName);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymText(JNIEnv *env, jobject obj, jint symIndex, jbyteArray q, jobjectArray symTxt);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymType(JNIEnv *env, jobject obj, jint symIndex);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymUserInfo(JNIEnv *env, jobject obj, jint symIndex);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymEntries(JNIEnv *env, jobject obj, jint symIndex);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymOffset(JNIEnv *env, jobject obj, jint symIndex);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymDomNames(JNIEnv *env, jobject obj, jint symIndex, jobjectArray symDoms, jintArray symDim);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymDomIdx(JNIEnv *env, jobject obj, jint symIndex, jintArray symDomIdx, jintArray symDim);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_DomNameCount(JNIEnv *env, jobject obj);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_DomName(JNIEnv *env, jobject obj, jint domIndex, jobjectArray domName);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_ColIndex(JNIEnv *env, jobject obj, jint symIndex, jintArray uelIndices);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_RowIndex(JNIEnv *env, jobject obj, jint symIndex, jintArray uelIndices);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_ColUels(JNIEnv *env, jobject obj, jint j, jintArray symIndex, jintArray uelIndices, jintArray symDim);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_RowUels(JNIEnv *env, jobject obj, jint i, jintArray symIndex, jintArray uelIndices, jintArray symDim);
JNIEXPORT jlong JNICALL Java_com_gams_api_dctm_FindFirstRowCol(JNIEnv *env, jobject obj, jint symIndex, jintArray uelIndices, jintArray rcIndex);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_FindNextRowCol(JNIEnv *env, jobject obj, jlong findHandle, jintArray rcIndex);
JNIEXPORT void JNICALL Java_com_gams_api_dctm_FindClose(JNIEnv *env, jobject obj, jlong findHandle);
JNIEXPORT jdouble JNICALL Java_com_gams_api_dctm_MemUsed(JNIEnv *env, jobject obj);
JNIEXPORT void JNICALL Java_com_gams_api_dctm_SetBasicCounts(JNIEnv *env, jobject obj, jint NRows, jint NCols, jint NBlocks);
JNIEXPORT jboolean JNICALL Java_com_gams_api_dctm_SetBasicCountsEx(JNIEnv *env, jobject obj, jint NRows, jint NCols, jlong NBlocks, jobjectArray Msg);
JNIEXPORT void JNICALL Java_com_gams_api_dctm_AddUel(JNIEnv *env, jobject obj, jstring uelLabel, jbyte q);
JNIEXPORT void JNICALL Java_com_gams_api_dctm_AddSymbol(JNIEnv *env, jobject obj, jstring symName, jint symTyp, jint symDim, jint userInfo, jstring symTxt);
JNIEXPORT void JNICALL Java_com_gams_api_dctm_AddSymbolData(JNIEnv *env, jobject obj, jintArray uelIndices);
JNIEXPORT jboolean JNICALL Java_com_gams_api_dctm_AddSymbolDoms(JNIEnv *env, jobject obj, jstring symName, jobjectArray symDoms, jint symDim, jobjectArray Msg);
JNIEXPORT void JNICALL Java_com_gams_api_dctm_WriteGDX(JNIEnv *env, jobject obj, jstring fName, jobjectArray Msg);
JNIEXPORT void JNICALL Java_com_gams_api_dctm_WriteGDXWithHandle(JNIEnv *env, jobject obj, jlong gdxptr, jobjectArray Msg);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NRows(JNIEnv *env, jobject obj);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NCols(JNIEnv *env, jobject obj);
JNIEXPORT jint JNICALL Java_com_gams_api_dctm_LrgDim(JNIEnv *env, jobject obj);

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_GetReady(JNIEnv *env, jobject obj, jobjectArray msg)
{
   int rc_GetReady;
   jstring local_msg;
   char buffer_msg[256];
   rc_GetReady = dctGetReady(buffer_msg, sizeof(buffer_msg));
   local_msg = (*env)->NewStringUTF(env, buffer_msg);
   (*env)->SetObjectArrayElement(env, msg, 0, local_msg);
   return rc_GetReady;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_GetReadyD(JNIEnv *env, jobject obj, jstring dirName, jobjectArray msg)
{
   int rc_GetReadyD;
   char *local_dirName;
   jstring local_msg;
   char buffer_msg[256];
   buffer_msg[0] = '\0';
   local_dirName = (char *) (*env)->GetStringUTFChars(env, dirName, NULL);
   rc_GetReadyD = dctGetReadyD(local_dirName, buffer_msg, sizeof(buffer_msg));
   (*env)->ReleaseStringUTFChars(env, dirName, local_dirName);
   local_msg = (*env)->NewStringUTF(env, buffer_msg);
   (*env)->SetObjectArrayElement(env, msg, 0, local_msg);
   return rc_GetReadyD;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_GetReadyL(JNIEnv *env, jobject obj, jstring libName, jobjectArray msg)
{
   int rc_GetReadyL;
   char *local_libName;
   jstring local_msg;
   char buffer_msg[256];
   buffer_msg[0] = '\0';
   local_libName = (char *) (*env)->GetStringUTFChars(env, libName, NULL);
   rc_GetReadyL = dctGetReadyL(local_libName, buffer_msg, sizeof(buffer_msg));
   (*env)->ReleaseStringUTFChars(env, libName, local_libName);
   local_msg = (*env)->NewStringUTF(env, buffer_msg);
   (*env)->SetObjectArrayElement(env, msg, 0, local_msg);
   return rc_GetReadyL;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_Create(JNIEnv *env, jobject obj, jobjectArray msg)
{
   int rc_Create;
   jfieldID fid;
   u64_t pdct;
   jstring local_msg;
   char buffer_msg[256];
   jclass cls = (*env)->GetObjectClass(env, obj);
   buffer_msg[0] = '\0';
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_Create = dctCreate((dctHandle_t *)&pdct.p, buffer_msg, sizeof(buffer_msg));
   local_msg = (*env)->NewStringUTF(env, buffer_msg);
   (*env)->SetObjectArrayElement(env, msg, 0, local_msg);
   (*env)->SetLongField(env, obj, fid, pdct.i);
   return rc_Create;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_CreateD(JNIEnv *env, jobject obj, jstring dirName, jobjectArray msg)
{
   int rc_CreateD;
   jfieldID fid;
   u64_t pdct;
   char *local_dirName;
   jstring local_msg;
   char buffer_msg[256];
   jclass cls = (*env)->GetObjectClass(env, obj);
   buffer_msg[0] = '\0';
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_dirName = (char *) (*env)->GetStringUTFChars(env, dirName, NULL);
   rc_CreateD = dctCreateD((dctHandle_t *)&pdct.p, local_dirName, buffer_msg, sizeof(buffer_msg));
   (*env)->ReleaseStringUTFChars(env, dirName, local_dirName);
   local_msg = (*env)->NewStringUTF(env, buffer_msg);
   (*env)->SetObjectArrayElement(env, msg, 0, local_msg);
   (*env)->SetLongField(env, obj, fid, pdct.i);
   return rc_CreateD;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_CreateDD(JNIEnv *env, jobject obj, jstring dirName, jobjectArray msg)
{
   int rc_CreateDD;
   jfieldID fid;
   u64_t pdct;
   char *local_dirName;
   jstring local_msg;
   char buffer_msg[256];
   jclass cls = (*env)->GetObjectClass(env, obj);
   buffer_msg[0] = '\0';
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_dirName = (char *) (*env)->GetStringUTFChars(env, dirName, NULL);
   rc_CreateDD = dctCreateDD((dctHandle_t *)&pdct.p, local_dirName, buffer_msg, sizeof(buffer_msg));
   (*env)->ReleaseStringUTFChars(env, dirName, local_dirName);
   local_msg = (*env)->NewStringUTF(env, buffer_msg);
   (*env)->SetObjectArrayElement(env, msg, 0, local_msg);
   (*env)->SetLongField(env, obj, fid, pdct.i);
   return rc_CreateDD;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_CreateL(JNIEnv *env, jobject obj, jstring libName, jobjectArray msg)
{
   int rc_CreateL;
   jfieldID fid;
   u64_t pdct;
   char *local_libName;
   jstring local_msg;
   char buffer_msg[256];
   jclass cls = (*env)->GetObjectClass(env, obj);
   buffer_msg[0] = '\0';
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_libName = (char *) (*env)->GetStringUTFChars(env, libName, NULL);
   rc_CreateL = dctCreateL((dctHandle_t *)&pdct.p, local_libName, buffer_msg, sizeof(buffer_msg));
   (*env)->ReleaseStringUTFChars(env, libName, local_libName);
   local_msg = (*env)->NewStringUTF(env, buffer_msg);
   (*env)->SetObjectArrayElement(env, msg, 0, local_msg);
   (*env)->SetLongField(env, obj, fid, pdct.i);
   return rc_CreateL;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_Free(JNIEnv *env, jobject obj)
{
   int rc_Free;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_Free = dctFree((dctHandle_t *)&pdct.p);
   (*env)->SetLongField(env, obj, fid, pdct.i);
   return rc_Free;
}


JNIEXPORT jint JNICALL Java_com_gams_api_dctm_LoadEx(JNIEnv *env, jobject obj, jstring fName, jobjectArray Msg)
{
   int rc_dctLoadEx;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_fName;
   jstring local_Msg;
   char buffer_Msg[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_fName = (char *)(*env)->GetStringUTFChars(env, fName, NULL);
   buffer_Msg[0] = '\0';
   rc_dctLoadEx = dctLoadEx((dctHandle_t)pdct.p, local_fName, buffer_Msg, 256);
   (*env)->ReleaseStringUTFChars(env, fName, local_fName);
   local_Msg = (*env)->NewStringUTF(env, buffer_Msg);
   (*env)->SetObjectArrayElement(env, Msg, 0, local_Msg);
   return rc_dctLoadEx;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_LoadWithHandle(JNIEnv *env, jobject obj, jlong gdxptr, jobjectArray Msg)
{
   int rc_dctLoadWithHandle;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   u64_t local_gdxptr;
   jstring local_Msg;
   char buffer_Msg[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_gdxptr.i = gdxptr;
   buffer_Msg[0] = '\0';
   rc_dctLoadWithHandle = dctLoadWithHandle((dctHandle_t)pdct.p, local_gdxptr.p, buffer_Msg, 256);
   local_Msg = (*env)->NewStringUTF(env, buffer_Msg);
   (*env)->SetObjectArrayElement(env, Msg, 0, local_Msg);
   return rc_dctLoadWithHandle;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NUels(JNIEnv *env, jobject obj)
{
   int rc_dctNUels;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctNUels = dctNUels((dctHandle_t)pdct.p);
   return rc_dctNUels;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_UelIndex(JNIEnv *env, jobject obj, jstring uelLabel)
{
   int rc_dctUelIndex;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_uelLabel;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelLabel = (char *)(*env)->GetStringUTFChars(env, uelLabel, NULL);
   rc_dctUelIndex = dctUelIndex((dctHandle_t)pdct.p, local_uelLabel);
   (*env)->ReleaseStringUTFChars(env, uelLabel, local_uelLabel);
   return rc_dctUelIndex;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_UelLabel(JNIEnv *env, jobject obj, jint uelIndex, jbyteArray q, jobjectArray uelLabel)
{
   int rc_dctUelLabel;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_q;
   jstring local_uelLabel;
   char buffer_uelLabel[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_q = (char *)(*env)->GetByteArrayElements(env, q, NULL);
   buffer_uelLabel[0] = '\0';
   rc_dctUelLabel = dctUelLabel((dctHandle_t)pdct.p, uelIndex, local_q, buffer_uelLabel, 256);
   (*env)->ReleaseByteArrayElements(env, q, (jbyte *)local_q, 0);
   local_uelLabel = (*env)->NewStringUTF(env, buffer_uelLabel);
   (*env)->SetObjectArrayElement(env, uelLabel, 0, local_uelLabel);
   return rc_dctUelLabel;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NLSyms(JNIEnv *env, jobject obj)
{
   int rc_dctNLSyms;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctNLSyms = dctNLSyms((dctHandle_t)pdct.p);
   return rc_dctNLSyms;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymDim(JNIEnv *env, jobject obj, jint symIndex)
{
   int rc_dctSymDim;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctSymDim = dctSymDim((dctHandle_t)pdct.p, symIndex);
   return rc_dctSymDim;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymIndex(JNIEnv *env, jobject obj, jstring symName)
{
   int rc_dctSymIndex;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_symName;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_symName = (char *)(*env)->GetStringUTFChars(env, symName, NULL);
   rc_dctSymIndex = dctSymIndex((dctHandle_t)pdct.p, local_symName);
   (*env)->ReleaseStringUTFChars(env, symName, local_symName);
   return rc_dctSymIndex;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymName(JNIEnv *env, jobject obj, jint symIndex, jobjectArray symName)
{
   int rc_dctSymName;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   jstring local_symName;
   char buffer_symName[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   buffer_symName[0] = '\0';
   rc_dctSymName = dctSymName((dctHandle_t)pdct.p, symIndex, buffer_symName, 256);
   local_symName = (*env)->NewStringUTF(env, buffer_symName);
   (*env)->SetObjectArrayElement(env, symName, 0, local_symName);
   return rc_dctSymName;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymText(JNIEnv *env, jobject obj, jint symIndex, jbyteArray q, jobjectArray symTxt)
{
   int rc_dctSymText;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_q;
   jstring local_symTxt;
   char buffer_symTxt[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_q = (char *)(*env)->GetByteArrayElements(env, q, NULL);
   buffer_symTxt[0] = '\0';
   rc_dctSymText = dctSymText((dctHandle_t)pdct.p, symIndex, local_q, buffer_symTxt, 256);
   (*env)->ReleaseByteArrayElements(env, q, (jbyte *)local_q, 0);
   local_symTxt = (*env)->NewStringUTF(env, buffer_symTxt);
   (*env)->SetObjectArrayElement(env, symTxt, 0, local_symTxt);
   return rc_dctSymText;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymType(JNIEnv *env, jobject obj, jint symIndex)
{
   int rc_dctSymType;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctSymType = dctSymType((dctHandle_t)pdct.p, symIndex);
   return rc_dctSymType;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymUserInfo(JNIEnv *env, jobject obj, jint symIndex)
{
   int rc_dctSymUserInfo;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctSymUserInfo = dctSymUserInfo((dctHandle_t)pdct.p, symIndex);
   return rc_dctSymUserInfo;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymEntries(JNIEnv *env, jobject obj, jint symIndex)
{
   int rc_dctSymEntries;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctSymEntries = dctSymEntries((dctHandle_t)pdct.p, symIndex);
   return rc_dctSymEntries;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymOffset(JNIEnv *env, jobject obj, jint symIndex)
{
   int rc_dctSymOffset;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctSymOffset = dctSymOffset((dctHandle_t)pdct.p, symIndex);
   return rc_dctSymOffset;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymDomNames(JNIEnv *env, jobject obj, jint symIndex, jobjectArray symDoms, jintArray symDim)
{
   int rc_dctSymDomNames;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int i_symDoms, sidim_symDoms;
   stringUEL local_symDoms[GLOBAL_MAX_INDEX_DIM];
   char *ptr_symDoms[GLOBAL_MAX_INDEX_DIM];
   int local_symDim[1];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   sidim_symDoms = GLOBAL_MAX_INDEX_DIM;
   for(i_symDoms=0;i_symDoms<sidim_symDoms;++i_symDoms) {
      ptr_symDoms[i_symDoms] = local_symDoms[i_symDoms];
   }
   rc_dctSymDomNames = dctSymDomNames((dctHandle_t)pdct.p, symIndex, ptr_symDoms, local_symDim);
   sidim_symDoms = GLOBAL_MAX_INDEX_DIM;
   for(i_symDoms=0;i_symDoms<sidim_symDoms;++i_symDoms) {
      jstring js_;
      js_ = (*env)->NewStringUTF(env, local_symDoms[i_symDoms]);
      (*env)->SetObjectArrayElement(env, symDoms, i_symDoms, js_);
   }
   (*env)->SetIntArrayRegion(env, symDim, 0, 1, local_symDim);
   return rc_dctSymDomNames;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_SymDomIdx(JNIEnv *env, jobject obj, jint symIndex, jintArray symDomIdx, jintArray symDim)
{
   int rc_dctSymDomIdx;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int *local_symDomIdx;
   int local_symDim[1];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_symDomIdx = (*env)->GetIntArrayElements(env, symDomIdx, NULL);
   rc_dctSymDomIdx = dctSymDomIdx((dctHandle_t)pdct.p, symIndex, local_symDomIdx, local_symDim);
   (*env)->ReleaseIntArrayElements(env, symDomIdx, local_symDomIdx, 0);
   (*env)->SetIntArrayRegion(env, symDim, 0, 1, local_symDim);
   return rc_dctSymDomIdx;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_DomNameCount(JNIEnv *env, jobject obj)
{
   int rc_dctDomNameCount;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctDomNameCount = dctDomNameCount((dctHandle_t)pdct.p);
   return rc_dctDomNameCount;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_DomName(JNIEnv *env, jobject obj, jint domIndex, jobjectArray domName)
{
   int rc_dctDomName;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   jstring local_domName;
   char buffer_domName[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   buffer_domName[0] = '\0';
   rc_dctDomName = dctDomName((dctHandle_t)pdct.p, domIndex, buffer_domName, 256);
   local_domName = (*env)->NewStringUTF(env, buffer_domName);
   (*env)->SetObjectArrayElement(env, domName, 0, local_domName);
   return rc_dctDomName;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_ColIndex(JNIEnv *env, jobject obj, jint symIndex, jintArray uelIndices)
{
   int rc_dctColIndex;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int *local_uelIndices;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelIndices = (*env)->GetIntArrayElements(env, uelIndices, NULL);
   rc_dctColIndex = dctColIndex((dctHandle_t)pdct.p, symIndex, local_uelIndices);
   (*env)->ReleaseIntArrayElements(env, uelIndices, local_uelIndices, 0);
   return rc_dctColIndex;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_RowIndex(JNIEnv *env, jobject obj, jint symIndex, jintArray uelIndices)
{
   int rc_dctRowIndex;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int *local_uelIndices;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelIndices = (*env)->GetIntArrayElements(env, uelIndices, NULL);
   rc_dctRowIndex = dctRowIndex((dctHandle_t)pdct.p, symIndex, local_uelIndices);
   (*env)->ReleaseIntArrayElements(env, uelIndices, local_uelIndices, 0);
   return rc_dctRowIndex;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_ColUels(JNIEnv *env, jobject obj, jint j, jintArray symIndex, jintArray uelIndices, jintArray symDim)
{
   int rc_dctColUels;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int local_symIndex[1];
   int *local_uelIndices;
   int local_symDim[1];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelIndices = (*env)->GetIntArrayElements(env, uelIndices, NULL);
   rc_dctColUels = dctColUels((dctHandle_t)pdct.p, j, local_symIndex, local_uelIndices, local_symDim);
   (*env)->SetIntArrayRegion(env, symIndex, 0, 1, local_symIndex);
   (*env)->ReleaseIntArrayElements(env, uelIndices, local_uelIndices, 0);
   (*env)->SetIntArrayRegion(env, symDim, 0, 1, local_symDim);
   return rc_dctColUels;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_RowUels(JNIEnv *env, jobject obj, jint i, jintArray symIndex, jintArray uelIndices, jintArray symDim)
{
   int rc_dctRowUels;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int local_symIndex[1];
   int *local_uelIndices;
   int local_symDim[1];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelIndices = (*env)->GetIntArrayElements(env, uelIndices, NULL);
   rc_dctRowUels = dctRowUels((dctHandle_t)pdct.p, i, local_symIndex, local_uelIndices, local_symDim);
   (*env)->SetIntArrayRegion(env, symIndex, 0, 1, local_symIndex);
   (*env)->ReleaseIntArrayElements(env, uelIndices, local_uelIndices, 0);
   (*env)->SetIntArrayRegion(env, symDim, 0, 1, local_symDim);
   return rc_dctRowUels;
}

JNIEXPORT jlong JNICALL Java_com_gams_api_dctm_FindFirstRowCol(JNIEnv *env, jobject obj, jint symIndex, jintArray uelIndices, jintArray rcIndex)
{
   u64_t rc_dctFindFirstRowCol;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int *local_uelIndices;
   int local_rcIndex[1];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return (jlong) NULL;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelIndices = (*env)->GetIntArrayElements(env, uelIndices, NULL);
   rc_dctFindFirstRowCol.p = dctFindFirstRowCol((dctHandle_t)pdct.p, symIndex, local_uelIndices, local_rcIndex);
   (*env)->ReleaseIntArrayElements(env, uelIndices, local_uelIndices, 0);
   (*env)->SetIntArrayRegion(env, rcIndex, 0, 1, local_rcIndex);
   return rc_dctFindFirstRowCol.i;
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_FindNextRowCol(JNIEnv *env, jobject obj, jlong findHandle, jintArray rcIndex)
{
   int rc_dctFindNextRowCol;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   u64_t local_findHandle;
   int local_rcIndex[1];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_findHandle.i = findHandle;
   rc_dctFindNextRowCol = dctFindNextRowCol((dctHandle_t)pdct.p, local_findHandle.p, local_rcIndex);
   (*env)->SetIntArrayRegion(env, rcIndex, 0, 1, local_rcIndex);
   return rc_dctFindNextRowCol;
}

JNIEXPORT void JNICALL Java_com_gams_api_dctm_FindClose(JNIEnv *env, jobject obj, jlong findHandle)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   u64_t local_findHandle;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return ;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_findHandle.i = findHandle;
   dctFindClose((dctHandle_t)pdct.p, local_findHandle.p);
}

JNIEXPORT jdouble JNICALL Java_com_gams_api_dctm_MemUsed(JNIEnv *env, jobject obj)
{
   double rc_dctMemUsed;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0.0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   rc_dctMemUsed = dctMemUsed((dctHandle_t)pdct.p);
   return rc_dctMemUsed;
}

JNIEXPORT void JNICALL Java_com_gams_api_dctm_SetBasicCounts(JNIEnv *env, jobject obj, jint NRows, jint NCols, jint NBlocks)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return ;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   dctSetBasicCounts((dctHandle_t)pdct.p, NRows, NCols, NBlocks);
}

JNIEXPORT jboolean JNICALL Java_com_gams_api_dctm_SetBasicCountsEx(JNIEnv *env, jobject obj, jint NRows, jint NCols, jlong NBlocks, jobjectArray Msg)
{
   int rc_dctSetBasicCountsEx;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   jstring local_Msg;
   char buffer_Msg[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   buffer_Msg[0] = '\0';
   rc_dctSetBasicCountsEx = dctSetBasicCountsEx((dctHandle_t)pdct.p, NRows, NCols, NBlocks, buffer_Msg, 256);
   local_Msg = (*env)->NewStringUTF(env, buffer_Msg);
   (*env)->SetObjectArrayElement(env, Msg, 0, local_Msg);
   return rc_dctSetBasicCountsEx;
}

JNIEXPORT void JNICALL Java_com_gams_api_dctm_AddUel(JNIEnv *env, jobject obj, jstring uelLabel, jbyte q)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_uelLabel;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return ;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelLabel = (char *)(*env)->GetStringUTFChars(env, uelLabel, NULL);
   dctAddUel((dctHandle_t)pdct.p, local_uelLabel, q);
   (*env)->ReleaseStringUTFChars(env, uelLabel, local_uelLabel);
}

JNIEXPORT void JNICALL Java_com_gams_api_dctm_AddSymbol(JNIEnv *env, jobject obj, jstring symName, jint symTyp, jint symDim, jint userInfo, jstring symTxt)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_symName;
   char *local_symTxt;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return ;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_symName = (char *)(*env)->GetStringUTFChars(env, symName, NULL);
   local_symTxt = (char *)(*env)->GetStringUTFChars(env, symTxt, NULL);
   dctAddSymbol((dctHandle_t)pdct.p, local_symName, symTyp, symDim, userInfo, local_symTxt);
   (*env)->ReleaseStringUTFChars(env, symName, local_symName);
   (*env)->ReleaseStringUTFChars(env, symTxt, local_symTxt);
}

JNIEXPORT void JNICALL Java_com_gams_api_dctm_AddSymbolData(JNIEnv *env, jobject obj, jintArray uelIndices)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   int *local_uelIndices;
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return ;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_uelIndices = (*env)->GetIntArrayElements(env, uelIndices, NULL);
   dctAddSymbolData((dctHandle_t)pdct.p, local_uelIndices);
   (*env)->ReleaseIntArrayElements(env, uelIndices, local_uelIndices, 0);
}

JNIEXPORT jboolean JNICALL Java_com_gams_api_dctm_AddSymbolDoms(JNIEnv *env, jobject obj, jstring symName, jobjectArray symDoms, jint symDim, jobjectArray Msg)
{
   int rc_dctAddSymbolDoms;
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_symName;
   int i_symDoms, sidim_symDoms;
   stringUEL local_symDoms[GLOBAL_MAX_INDEX_DIM];
   char *ptr_symDoms[GLOBAL_MAX_INDEX_DIM];
   jstring local_Msg;
   char buffer_Msg[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_symName = (char *)(*env)->GetStringUTFChars(env, symName, NULL);
   buffer_Msg[0] = '\0';
   sidim_symDoms = GLOBAL_MAX_INDEX_DIM;
   for(i_symDoms=0;i_symDoms<sidim_symDoms;++i_symDoms) {
      jstring js_;
      char *str_;
      ptr_symDoms[i_symDoms] = local_symDoms[i_symDoms];
      js_ = (*env)->GetObjectArrayElement(env, symDoms, i_symDoms);
      if (js_==NULL)
          js_ = (*env)->NewStringUTF(env, "");
      str_ = (char*) (*env)->GetStringUTFChars(env, js_, NULL);
      strncpy(local_symDoms[i_symDoms], str_, GLOBAL_UEL_IDENT_SIZE-1);
      local_symDoms[i_symDoms][GLOBAL_UEL_IDENT_SIZE-1] = '\0';
      (*env)->ReleaseStringUTFChars(env, js_, str_);
   }
   rc_dctAddSymbolDoms = dctAddSymbolDoms((dctHandle_t)pdct.p, local_symName, (const char **)ptr_symDoms, symDim, buffer_Msg, 256);
   (*env)->ReleaseStringUTFChars(env, symName, local_symName);
   local_Msg = (*env)->NewStringUTF(env, buffer_Msg);
   (*env)->SetObjectArrayElement(env, Msg, 0, local_Msg);
   return rc_dctAddSymbolDoms;
}

JNIEXPORT void JNICALL Java_com_gams_api_dctm_WriteGDX(JNIEnv *env, jobject obj, jstring fName, jobjectArray Msg)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   char *local_fName;
   jstring local_Msg;
   char buffer_Msg[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return ;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_fName = (char *)(*env)->GetStringUTFChars(env, fName, NULL);
   buffer_Msg[0] = '\0';
   dctWriteGDX((dctHandle_t)pdct.p, local_fName, buffer_Msg);
   (*env)->ReleaseStringUTFChars(env, fName, local_fName);
   local_Msg = (*env)->NewStringUTF(env, buffer_Msg);
   (*env)->SetObjectArrayElement(env, Msg, 0, local_Msg);
}

JNIEXPORT void JNICALL Java_com_gams_api_dctm_WriteGDXWithHandle(JNIEnv *env, jobject obj, jlong gdxptr, jobjectArray Msg)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   u64_t local_gdxptr;
   jstring local_Msg;
   char buffer_Msg[256];
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return ;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   local_gdxptr.i = gdxptr;
   buffer_Msg[0] = '\0';
   dctWriteGDXWithHandle((dctHandle_t)pdct.p, local_gdxptr.p, buffer_Msg);
   local_Msg = (*env)->NewStringUTF(env, buffer_Msg);
   (*env)->SetObjectArrayElement(env, Msg, 0, local_Msg);
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NRows(JNIEnv *env, jobject obj)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   return dctNRows((dctHandle_t)pdct.p);
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_NCols(JNIEnv *env, jobject obj)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   return dctNCols((dctHandle_t)pdct.p);
}

JNIEXPORT jint JNICALL Java_com_gams_api_dctm_LrgDim(JNIEnv *env, jobject obj)
{
   jfieldID fid;
   u64_t pdct;
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env, cls, "dctPtr", "J");
   if (fid == NULL) return 0;
   pdct.i = (*env)->GetLongField(env, obj, fid);
   return dctLrgDim((dctHandle_t)pdct.p);
}
