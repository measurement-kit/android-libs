#include "mk-jni-api.h"

#include "measurement_kit/vendor/mkgeoip.h"

JNIEXPORT jboolean JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_Good(JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_good_v2(
      (mkgeoip_lookup_results_t *)handle) : JNI_FALSE;
}

JNIEXPORT jdouble JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetBytesSent(
    JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_get_bytes_sent_v2(
      (mkgeoip_lookup_results_t *)handle) : 0.0;
}

JNIEXPORT jdouble JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetBytesRecv(
    JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_get_bytes_recv_v2(
      (mkgeoip_lookup_results_t *)handle) : 0.0;
}

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeIP(
    JNIEnv *env, jclass, jlong handle) {
  if (env == nullptr || handle == 0) return nullptr;
  const char *s = mkgeoip_lookup_results_get_probe_ip_v2(
      (mkgeoip_lookup_results_t *)handle);
  return (s != nullptr) ? env->NewStringUTF(s) : nullptr;
}

JNIEXPORT jlong JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeASN(
    JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_get_probe_asn_v2(
      (mkgeoip_lookup_results_t *)handle) : 0;
}

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeCC(
    JNIEnv *env, jclass, jlong handle) {
  if (env == nullptr || handle == 0) return nullptr;
  const char *s = mkgeoip_lookup_results_get_probe_cc_v2(
      (mkgeoip_lookup_results_t *)handle);
  return (s != nullptr) ? env->NewStringUTF(s) : nullptr;
}

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeOrg(
    JNIEnv *env, jclass, jlong handle) {
  if (env == nullptr || handle == 0) return nullptr;
  const char *s = mkgeoip_lookup_results_get_probe_org_v2(
      (mkgeoip_lookup_results_t *)handle);
  return (s != nullptr) ? env->NewStringUTF(s) : nullptr;
}

JNIEXPORT jbyteArray JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetLogs(
    JNIEnv *env, jclass, jlong handle) {
  if (env == nullptr || handle == 0) return nullptr;
  const uint8_t *base = nullptr;
  size_t count = 0;
  // Implementation note: both in Java and Android jsize is jint
  mkgeoip_lookup_results_get_logs_binary_v2(
      (mkgeoip_lookup_results_t *)handle, &base, &count);
  if (base == nullptr || count <= 0 || count > INT_MAX) {
    return nullptr;
  }
  jbyteArray array = env->NewByteArray((jsize)count);
  if (array == nullptr) return nullptr;
  env->SetByteArrayRegion(array, 0, (jsize)count, (const jbyte *)base);
  return array;
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_Delete(JNIEnv *, jclass, jlong handle) {
  mkgeoip_lookup_results_delete((mkgeoip_lookup_results_t *)handle);
}

JNIEXPORT jlong JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_New(JNIEnv *, jclass) {
  return (jlong)mkgeoip_lookup_settings_new_nonnull();
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_SetTimeout(
    JNIEnv *, jclass, jlong handle, jlong timeout) {
  if (handle != 0 && timeout >= 0) {
      mkgeoip_lookup_settings_set_timeout_v2(
        (mkgeoip_lookup_settings_t *)handle, timeout);
  }
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_SetCountryDBPath(
    JNIEnv *env, jclass, jlong handle, jstring str) {
  if (env != nullptr && handle != 0 && str != nullptr) {
    const char *s = env->GetStringUTFChars(str, nullptr);
    if (s != nullptr) {
      mkgeoip_lookup_settings_set_country_db_path_v2(
        (mkgeoip_lookup_settings_t *)handle, s);
      env->ReleaseStringUTFChars(str, s);
    }
  }
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_SetASNDBPath(
    JNIEnv *env, jclass, jlong handle, jstring str) {
  if (env != nullptr && handle != 0 && str != nullptr) {
    const char *s = env->GetStringUTFChars(str, nullptr);
    if (s != nullptr) {
      mkgeoip_lookup_settings_set_asn_db_path_v2(
        (mkgeoip_lookup_settings_t *)handle, s);
      env->ReleaseStringUTFChars(str, s);
    }
  }
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_SetCABundlePath(
    JNIEnv *env, jclass, jlong handle, jstring str) {
  if (env != nullptr && handle != 0 && str != nullptr) {
    const char *s = env->GetStringUTFChars(str, nullptr);
    if (s != nullptr) {
      mkgeoip_lookup_settings_set_ca_bundle_path_v2(
        (mkgeoip_lookup_settings_t *)handle, s);
      env->ReleaseStringUTFChars(str, s);
    }
  }
}

JNIEXPORT jlong JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_Perform(JNIEnv *, jclass, jlong handle) {
  return (jlong)mkgeoip_lookup_settings_perform_nonnull(
      (mkgeoip_lookup_settings_t *)handle);
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_Delete(JNIEnv *, jclass, jlong handle) {
  mkgeoip_lookup_settings_delete((mkgeoip_lookup_settings_t *)handle);
}
