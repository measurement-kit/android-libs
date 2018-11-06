// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mk-jni-api.h"
#include "mk-jni-util.hpp"

#include "measurement_kit/mkapi/orchestra.h"

MKJNI_GET_BOOLEAN(MKOrchestraResult_Good,
                  mkapi_orchestra_result_good,
                  mkapi_orchestra_result_t)

MKJNI_GET_BYTE_ARRAY(MKOrchestraResult_GetBinaryLogs,
                     mkapi_orchestra_result_get_binary_logs,
                     mkapi_orchestra_result_t)

MKJNI_DELETE(MKOrchestraResult_Delete,
             mkapi_orchestra_result_delete,
             mkapi_orchestra_result_t)

MKJNI_NEW(MKOrchestraClient_New,
          mkapi_orchestra_client_new)

MKJNI_SET_STRING(MKOrchestraClient_SetAvailableBandwidth,
                 mkapi_orchestra_client_set_available_bandwidth,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetDeviceToken,
                 mkapi_orchestra_client_set_device_token,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetCABundlePath,
                 mkapi_orchestra_client_set_ca_bundle_path,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetGeoIPCountryPath,
                 mkapi_orchestra_client_set_geoip_country_path,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetGeoIPASNPath,
                 mkapi_orchestra_client_set_geoip_asn_path,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetLanguage,
                 mkapi_orchestra_client_set_language,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetNetworkType,
                 mkapi_orchestra_client_set_network_type,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetPlatform,
                 mkapi_orchestra_client_set_platform,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetProbeASN,
                 mkapi_orchestra_client_set_probe_asn,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetProbeCC,
                 mkapi_orchestra_client_set_probe_cc,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetProbeFamily,
                 mkapi_orchestra_client_set_probe_family,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetProbeTimezone,
                 mkapi_orchestra_client_set_probe_timezone,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetRegistryURL,
                 mkapi_orchestra_client_set_registry_url,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetSecretsFile,
                 mkapi_orchestra_client_set_secrets_file,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetSoftwareName,
                 mkapi_orchestra_client_set_software_name,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_SetSoftwareVersion,
                 mkapi_orchestra_client_set_software_version,
                 mkapi_orchestra_client_t)

MKJNI_SET_STRING(MKOrchestraClient_AddSupportedTest,
                 mkapi_orchestra_client_add_supported_test,
                 mkapi_orchestra_client_t)

MKJNI_SET_LONG(MKOrchestraClient_SetTimeout,
               mkapi_orchestra_client_set_timeout,
               mkapi_orchestra_client_t)

MKJNI_GET_POINTER(MKOrchestraClient_Sync,
                  mkapi_orchestra_client_sync,
                  mkapi_orchestra_client_t)

MKJNI_DELETE(MKOrchestraClient_Delete,
             mkapi_orchestra_client_delete,
             mkapi_orchestra_client_t)
