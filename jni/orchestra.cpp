// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mkall.h"

#include <limits.h>

#include <measurement_kit/internal/mkapi/orchestra.h>

#include "mkall_util.h"

MKALL_GET_BOOLEAN(MKOrchestraResults_Good,
                  mkapi_orchestra_result_good,
                  mkapi_orchestra_result_t)

MKALL_GET_LOGS_FROM_BINARY_ARRAY(MKOrchestraResults_GetLogs,
                                 mkapi_orchestra_result_get_binary_logs,
                                 mkapi_orchestra_result_t)

MKALL_DELETE(MKOrchestraResults_Delete,
             mkapi_orchestra_result_delete,
             mkapi_orchestra_result_t)

MKALL_NEW(MKOrchestraSettings_New,
          mkapi_orchestra_client_new)

MKALL_SET_STRING(MKOrchestraSettings_SetAvailableBandwidth,
                 mkapi_orchestra_client_set_available_bandwidth,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetDeviceToken,
                 mkapi_orchestra_client_set_device_token,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetCABundlePath,
                 mkapi_orchestra_client_set_ca_bundle_path,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetGeoIPCountryPath,
                 mkapi_orchestra_client_set_geoip_country_path,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetGeoIPASNPath,
                 mkapi_orchestra_client_set_geoip_asn_path,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetLanguage,
                 mkapi_orchestra_client_set_language,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetNetworkType,
                 mkapi_orchestra_client_set_network_type,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetPlatform,
                 mkapi_orchestra_client_set_platform,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetProbeASN,
                 mkapi_orchestra_client_set_probe_asn,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetProbeCC,
                 mkapi_orchestra_client_set_probe_cc,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetProbeFamily,
                 mkapi_orchestra_client_set_probe_family,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetProbeTimezone,
                 mkapi_orchestra_client_set_probe_timezone,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetRegistryURL,
                 mkapi_orchestra_client_set_registry_url,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetSecretsFile,
                 mkapi_orchestra_client_set_secrets_file,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetSoftwareName,
                 mkapi_orchestra_client_set_software_name,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_SetSoftwareVersion,
                 mkapi_orchestra_client_set_software_version,
                 mkapi_orchestra_client_t)

MKALL_SET_STRING(MKOrchestraSettings_AddSupportedTest,
                 mkapi_orchestra_client_add_supported_test,
                 mkapi_orchestra_client_t)

MKALL_SET_LONG(MKOrchestraSettings_SetTimeout,
               mkapi_orchestra_client_set_timeout,
               mkapi_orchestra_client_t)

MKALL_GET_POINTER(MKOrchestraSettings_Sync,
                  mkapi_orchestra_client_sync,
                  mkapi_orchestra_client_t)

MKALL_DELETE(MKOrchestraSettings_Delete,
             mkapi_orchestra_client_delete,
             mkapi_orchestra_client_t)
