// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mkall.h"

#include <limits.h>

#include <measurement_kit/internal/geoiplookup/geoiplookup.h>

#include "mkall_util.h"

MKALL_GET_BOOLEAN(MKGeoIPLookupResults_Good,
                  mk_geoiplookup_response_good,
                  mk_geoiplookup_response_t)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeIP,
                 mk_geoiplookup_response_ip,
                 mk_geoiplookup_response_t)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeASN,
                 mk_geoiplookup_response_asn,
                 mk_geoiplookup_response_t)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeCC,
                 mk_geoiplookup_response_cc,
                 mk_geoiplookup_response_t)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeOrg,
                 mk_geoiplookup_response_org,
                 mk_geoiplookup_response_t)

MKALL_GET_LOGS(MKGeoIPLookupResults_GetLogs,
               mk_geoiplookup_response_logs_size,
               mk_geoiplookup_response_logs_at,
               mk_geoiplookup_response_t)

MKALL_DELETE(MKGeoIPLookupResults_Delete,
             mk_geoiplookup_response_delete,
             mk_geoiplookup_response_t)

MKALL_NEW(MKGeoIPLookupSettings_New, mk_geoiplookup_request_new)

MKALL_SET_LONG(MKGeoIPLookupSettings_SetTimeout,
               mk_geoiplookup_request_set_timeout,
               mk_geoiplookup_request_t)

MKALL_SET_STRING(MKGeoIPLookupSettings_SetCountryDBPath,
                 mk_geoiplookup_request_set_country_db_path,
                 mk_geoiplookup_request_t)

MKALL_SET_STRING(MKGeoIPLookupSettings_SetASNDBPath,
                 mk_geoiplookup_request_set_asn_db_path,
                 mk_geoiplookup_request_t)

MKALL_SET_STRING(MKGeoIPLookupSettings_SetCABundlePath,
                 mk_geoiplookup_request_set_ca_bundle_path,
                 mk_geoiplookup_request_t)

MKALL_GET_POINTER(MKGeoIPLookupSettings_Perform,
                  mk_geoiplookup_perform,
                  mk_geoiplookup_request_t)

MKALL_DELETE(MKGeoIPLookupSettings_Delete,
             mk_geoiplookup_request_delete,
             mk_geoiplookup_request_t)
