/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class io_ooni_mk_FFI */

#ifndef _Included_io_ooni_mk_FFI
#define _Included_io_ooni_mk_FFI
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jlong JNICALL Java_io_ooni_mk_FFI_mk_1nettest_1start
  (JNIEnv *, jclass, jstring);

JNIEXPORT jboolean JNICALL Java_io_ooni_mk_FFI_mk_1task_1is_1done
  (JNIEnv *, jclass, jlong);

JNIEXPORT jlong JNICALL Java_io_ooni_mk_FFI_mk_1task_1wait_1for_1next_1event
  (JNIEnv *, jclass, jlong);

JNIEXPORT void JNICALL Java_io_ooni_mk_FFI_mk_1task_1interrupt
  (JNIEnv *, jclass, jlong);

JNIEXPORT void JNICALL Java_io_ooni_mk_FFI_mk_1task_1destroy
  (JNIEnv *, jclass, jlong);

JNIEXPORT jstring JNICALL Java_io_ooni_mk_FFI_mk_1event_1serialize
  (JNIEnv *, jclass, jlong);

JNIEXPORT void JNICALL Java_io_ooni_mk_FFI_mk_1event_1destroy
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif