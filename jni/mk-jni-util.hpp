// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
#ifndef JNI_MK_JNI_UTIL_HPP
#define JNI_MK_JNI_UTIL_HPP

/// MKJNI_THROW_RUNTIME_EXCEPTION throws a runtime exception.
#define MKJNI_THROW_RUNTIME_EXCEPTION(text)                    \
  do {                                                         \
    jclass exc = env->FindClass("java/lang/RuntimeException"); \
    if (exc == nullptr) {                                      \
      abort();                                                 \
    }                                                          \
    env->ThrowNew(exc, text);                                  \
  } while (0)

/// MKJNI_THROW_EINTERNAL throws an invalid argument error.
#define MKJNI_THROW_EINVAL MKJNI_THROW_RUNTIME_EXCEPTION("Invalid argument")

/// MKJNI_THROW_EINTERNAL throws an internal error.
#define MKJNI_THROW_EINTERNAL MKJNI_THROW_RUNTIME_EXCEPTION("Internal error")

/// MKJNI_CALL calls a void method of a type.
#define MKJNI_CALL(java_func, cxx_func, cxx_type)                   \
  JNIEXPORT void JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *, jclass, jlong handle) { \
    cxx_func((cxx_type *)handle);                                   \
  }

/// MKJNI_DELETE defines a JNI deleter for the specified type.
#define MKJNI_DELETE MKJNI_CALL

/// MKJNI_GET_BOOLEAN returns a boolean value.
#define MKJNI_GET_BOOLEAN(java_func, cxx_func, cxx_type)               \
  JNIEXPORT jboolean JNICALL                                           \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKJNI_THROW_EINVAL;                                              \
      return JNI_FALSE;                                                \
    }                                                                  \
    return cxx_func((cxx_type *)handle) ? JNI_TRUE : JNI_FALSE;        \
  }

/// MKJNI_GET_BYTE_ARRAY returns a byte array.
#define MKJNI_GET_BYTE_ARRAY(java_func, cxx_func, cxx_type)               \
  JNIEXPORT jbyteArray JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) {    \
    if (handle == 0) {                                                    \
      MKJNI_THROW_EINVAL;                                                 \
      return nullptr;                                                     \
    }                                                                     \
    const uint8_t *base = nullptr;                                        \
    size_t count = 0;                                                     \
    /* Implementation note: both in Java and Android jsize is jint */     \
    cxx_func((cxx_type *)handle, &base, &count);                          \
    if (base == nullptr || count <= 0 || count > INT_MAX) {               \
      MKJNI_THROW_EINTERNAL;                                              \
      return nullptr;                                                     \
    }                                                                     \
    jbyteArray array = env->NewByteArray((jsize)count);                   \
    if (array == nullptr) {                                               \
      /* Exception should already be pending. */                          \
      return nullptr;                                                     \
    }                                                                     \
    env->SetByteArrayRegion(array, 0, (jsize)count, (const jbyte *)base); \
    return array;                                                         \
  }

/// MKJNI_GET_DOUBLE returns a double value.
#define MKJNI_GET_DOUBLE(java_func, cxx_func, cxx_type)                \
  JNIEXPORT jdouble JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKJNI_THROW_EINVAL;                                              \
      return 0.0;                                                      \
    }                                                                  \
    return cxx_func((cxx_type *)handle);                               \
  }

/// MKJNI_GET_LONG returns a long value.
#define MKJNI_GET_LONG(java_func, cxx_func, cxx_type)                  \
  JNIEXPORT jlong JNICALL                                              \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKJNI_THROW_EINVAL;                                              \
      return 0L;                                                       \
    }                                                                  \
    return cxx_func((cxx_type *)handle);                               \
  }

/// MKJNI_GET_POINTER returns a pointer value.
#define MKJNI_GET_POINTER(java_func, cxx_func, cxx_type)               \
  JNIEXPORT jlong JNICALL                                              \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKJNI_THROW_EINVAL;                                              \
      return 0L;                                                       \
    }                                                                  \
    return (jlong)cxx_func((cxx_type *)handle);                        \
  }

/// MKJNI_GET_STRING returns a string value.
#define MKJNI_GET_STRING(java_func, cxx_func, cxx_type)                \
  JNIEXPORT jstring JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKJNI_THROW_EINVAL;                                              \
      return nullptr;                                                  \
    }                                                                  \
    const char *s = cxx_func((cxx_type *)handle);                      \
    if (s == nullptr) {                                                \
      MKJNI_THROW_EINTERNAL;                                           \
      return nullptr;                                                  \
    }                                                                  \
    return env->NewStringUTF(s);                                       \
  }

/// MKJNI_NEW returns a new instance of a type.
#define MKJNI_NEW(java_func, cxx_func)                       \
  JNIEXPORT jlong JNICALL                                    \
      Java_io_ooni_mk_##java_func(JNIEnv *, jclass, jlong) { \
    return (jlong)cxx_func();                                \
  }

/// MKJNI_NEW_WITH_STRING_ARGUMENT returns a new instance of a type.
#define MKJNI_NEW_WITH_STRING_ARGUMENT(java_func, cxx_func) \
  JNIEXPORT jlong JNICALL Java_io_ooni_mk_##java_func(      \
      JNIEnv *env, jclass, jstring value) {                 \
    if (value == nullptr) {                                 \
      MKJNI_THROW_EINVAL;                                   \
      return 0L;                                            \
    }                                                       \
    const char *s = env->GetStringUTFChars(value, nullptr); \
    if (s == nullptr) {                                     \
      /* Exception should already be pending. */            \
      return 0L;                                            \
    }                                                       \
    jlong rv = (jlong)cxx_func(s);                          \
    env->ReleaseStringUTFChars(value, s);                   \
    return rv;                                              \
  }

/// MKJNI_SET_LONG sets a long value.
#define MKJNI_SET_LONG(java_func, cxx_func, cxx_type)   \
  JNIEXPORT void JNICALL Java_io_ooni_mk_##java_func(   \
      JNIEnv *env, jclass, jlong handle, jlong value) { \
    if (handle == 0) {                                  \
      MKJNI_THROW_EINVAL;                               \
      return;                                           \
    }                                                   \
    cxx_func((cxx_type *)handle, value);                \
  }

/// MKJNI_SET_STRING sets a string value.
#define MKJNI_SET_STRING(java_func, cxx_func, cxx_type)     \
  JNIEXPORT void JNICALL Java_io_ooni_mk_##java_func(       \
      JNIEnv *env, jclass, jlong handle, jstring value) {   \
    if (handle == 0 || value == nullptr) {                  \
      MKJNI_THROW_EINVAL;                                   \
      return;                                               \
    }                                                       \
    const char *s = env->GetStringUTFChars(value, nullptr); \
    if (s == nullptr) {                                     \
      /* Exception should already be pending. */            \
      return;                                               \
    }                                                       \
    cxx_func((cxx_type *)handle, s);                        \
    env->ReleaseStringUTFChars(value, s);                   \
  }

#endif  // JNI_MK_JNI_UTIL_HPP
