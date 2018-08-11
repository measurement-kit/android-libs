#include "FFI.h"

#include "measurement_kit/ffi.h"

JNIEXPORT jlong JNICALL Java_io_ooni_mk_FFI_mk_1nettest_1start(JNIEnv *env,
                                                               jclass,
                                                               jstring str) {
    jlong rv = 0;
    const char *s = env->GetStringUTFChars(str, nullptr);
    if (s != nullptr) {
        rv = (jlong)mk_nettest_start(s);
        env->ReleaseStringUTFChars(str, s);
    }
    return rv;
}

JNIEXPORT jboolean JNICALL
Java_io_ooni_mk_FFI_mk_1task_1is_1done(JNIEnv *, jclass, jlong nt) {
    return mk_task_is_done((mk_task_t *)nt) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jlong JNICALL Java_io_ooni_mk_FFI_mk_1task_1wait_1for_1next_1event(
        JNIEnv *, jclass, jlong nt) {
    return (jlong)mk_task_wait_for_next_event((mk_task_t *)nt);
}

JNIEXPORT void JNICALL Java_io_ooni_mk_FFI_mk_1task_1interrupt(JNIEnv *,
                                                                  jclass,
                                                                  jlong nt) {
    mk_task_interrupt((mk_task_t *)nt);
}

JNIEXPORT void JNICALL Java_io_ooni_mk_FFI_mk_1task_1destroy(JNIEnv *,
                                                                jclass,
                                                                jlong nt) {
    mk_task_destroy((mk_task_t *)nt);
}

JNIEXPORT jstring JNICALL Java_io_ooni_mk_FFI_mk_1event_1serialize(JNIEnv *env,
                                                                   jclass,
                                                                   jlong ev) {
    jstring rv = nullptr;
    const char *s = mk_event_serialize((mk_event_t *)ev);
    if (s != nullptr) {
        rv = env->NewStringUTF(s);
    }
    return rv;
}

JNIEXPORT void JNICALL Java_io_ooni_mk_FFI_mk_1event_1destroy(JNIEnv *,
                                                              jclass,
                                                              jlong ev) {
    mk_event_destroy((mk_event_t *)ev);
}
