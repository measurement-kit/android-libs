// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mk-jni-api.h"
#include "mk-jni-util.hpp"

#include "measurement_kit/vendor/mkgeoip.h"

MKJNI_GET_BOOLEAN(MKGeoIPLookupResults_Good,
                  mkgeoip_lookup_results_good_v2,
                  mkgeoip_lookup_results_t)

MKJNI_GET_DOUBLE(MKGeoIPLookupResults_GetBytesSent,
                 mkgeoip_lookup_results_get_bytes_sent_v2,
                 mkgeoip_lookup_results_t)

MKJNI_GET_DOUBLE(MKGeoIPLookupResults_GetBytesRecv,
                 mkgeoip_lookup_results_get_bytes_recv_v2,
                 mkgeoip_lookup_results_t)

MKJNI_GET_STRING(MKGeoIPLookupResults_GetProbeIP,
                 mkgeoip_lookup_results_get_probe_ip_v2,
                 mkgeoip_lookup_results_t)

MKJNI_GET_LONG(MKGeoIPLookupResults_GetProbeASN,
               mkgeoip_lookup_results_get_probe_asn_v2,
               mkgeoip_lookup_results_t)

MKJNI_GET_STRING(MKGeoIPLookupResults_GetProbeCC,
                 mkgeoip_lookup_results_get_probe_cc_v2,
                 mkgeoip_lookup_results_t)

MKJNI_GET_STRING(MKGeoIPLookupResults_GetProbeOrg,
                 mkgeoip_lookup_results_get_probe_org_v2,
                 mkgeoip_lookup_results_t)

MKJNI_GET_BYTE_ARRAY(MKGeoIPLookupResults_GetLogs,
                     mkgeoip_lookup_results_get_logs_binary_v2,
                     mkgeoip_lookup_results_t)

MKJNI_DELETE(MKGeoIPLookupResults_Delete,
             mkgeoip_lookup_results_delete,
             mkgeoip_lookup_results_t)

MKJNI_NEW(MKGeoIPLookupSettings_New, mkgeoip_lookup_settings_new_nonnull)

MKJNI_SET_LONG(MKGeoIPLookupSettings_SetTimeout,
               mkgeoip_lookup_settings_set_timeout_v2,
               mkgeoip_lookup_settings_t)

MKJNI_SET_STRING(MKGeoIPLookupSettings_SetCountryDBPath,
                 mkgeoip_lookup_settings_set_country_db_path_v2,
                 mkgeoip_lookup_settings_t)

MKJNI_SET_STRING(MKGeoIPLookupSettings_SetASNDBPath,
                 mkgeoip_lookup_settings_set_asn_db_path_v2,
                 mkgeoip_lookup_settings_t)

MKJNI_SET_STRING(MKGeoIPLookupSettings_SetCABundlePath,
                 mkgeoip_lookup_settings_set_ca_bundle_path_v2,
                 mkgeoip_lookup_settings_t)

MKJNI_GET_POINTER(MKGeoIPLookupSettings_Perform,
                  mkgeoip_lookup_settings_perform_nonnull,
                  mkgeoip_lookup_settings_t)

MKJNI_DELETE(MKGeoIPLookupSettings_Delete,
             mkgeoip_lookup_settings_delete,
             mkgeoip_lookup_settings_t)
