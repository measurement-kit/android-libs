#include "MKGeoIPLookup.h"

#include <measurement_kit/vendor/mkgeoip.h>

JNIEXPORT jboolean JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_Good(JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_good(
      (mkgeoip_lookup_results_t *)handle) : JNI_FALSE;
}

JNIEXPORT jdouble JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetBytesSent(
    JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_get_bytes_sent(
      (mkgeoip_lookup_results_t *)handle) : 0.0;
}

JNIEXPORT jdouble JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetBytesRecv(
    JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_get_bytes_recv(
      (mkgeoip_lookup_results_t *)handle) : 0.0;
}

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeIP(
    JNIEnv *env, jclass, jlong handle) {
  if (env == nullptr || handle == 0) return nullptr;
  const char *s = mkgeoip_lookup_results_get_probe_ip(
      (mkgeoip_lookup_results_t *)handle);
  return (s != nullptr) ? env->NewStringUTF(s) : nullptr;
}

JNIEXPORT jlong JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeASN(
    JNIEnv *, jclass, jlong handle) {
  return (handle != 0) ? mkgeoip_lookup_results_get_probe_asn(
      (mkgeoip_lookup_results_t *)handle) : 0;
}

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeCC(
    JNIEnv *env, jclass, jlong handle) {
  if (env == nullptr || handle == 0) return nullptr;
  const char *s = mkgeoip_lookup_results_get_probe_cc(
      (mkgeoip_lookup_results_t *)handle);
  return (s != nullptr) ? env->NewStringUTF(s) : nullptr;
}

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKGeoIPLookupResults_GetProbeOrg(
    JNIEnv *env, jclass, jlong handle) {
  if (env == nullptr || handle == 0) return nullptr;
  const char *s = mkgeoip_lookup_results_get_probe_org(
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
  if (!mkgeoip_lookup_results_get_logs_binary(
        (mkgeoip_lookup_results_t *)handle, &base, &count)
      || base == nullptr || count <= 0 || count > INT_MAX) {
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
  return (jlong)mkgeoip_lookup_settings_new();
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_SetTimeout(
    JNIEnv *, jclass, jlong handle, jlong timeout) {
  if (handle != 0 && timeout >= 0) {
      mkgeoip_lookup_settings_set_timeout(
        (mkgeoip_lookup_settings_t *)handle, timeout);
  }
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_SetCountryDBPath(
    JNIEnv *env, jclass, jlong handle, jstring str) {
  if (env != nullptr && handle != 0 && str != nullptr) {
    const char *s = env->GetStringUTFChars(str, nullptr);
    if (s != nullptr) {
      mkgeoip_lookup_settings_set_country_db_path(
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
      mkgeoip_lookup_settings_set_asn_db_path(
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
      mkgeoip_lookup_settings_set_ca_bundle_path(
        (mkgeoip_lookup_settings_t *)handle, s);
      env->ReleaseStringUTFChars(str, s);
    }
  }
}

JNIEXPORT jlong JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_Perform(JNIEnv *, jclass, jlong handle) {
  return (jlong)mkgeoip_lookup_settings_perform(
      (mkgeoip_lookup_settings_t *)handle);
}

JNIEXPORT void JNICALL
Java_io_ooni_mk_MKGeoIPLookupSettings_Delete(JNIEnv *, jclass, jlong handle) {
  mkgeoip_lookup_settings_delete((mkgeoip_lookup_settings_t *)handle);
}
