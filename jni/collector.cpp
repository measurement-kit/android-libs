// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mkall.h"

#include <measurement_kit/internal/collector/collector.h>

#include "mkall_util.h"

MKALL_GET_BOOLEAN(MKCollectorResubmitResults_Good,
                  mk_collector_resubmit_response_good,
                  mk_collector_resubmit_response_t)

MKALL_GET_STRING(MKCollectorResubmitResults_Content,
                 mk_collector_resubmit_response_content,
                 mk_collector_resubmit_response_t)

MKALL_GET_STRING(MKCollectorResubmitResults_ReportID,
                 mk_collector_resubmit_response_report_id,
                 mk_collector_resubmit_response_t)

MKALL_GET_LOGS(MKCollectorResubmitResults_Logs,
               mk_collector_resubmit_response_logs_size,
               mk_collector_resubmit_response_logs_at,
               mk_collector_resubmit_response_t)

MKALL_DELETE(MKCollectorResubmitResults_Delete,
             mk_collector_resubmit_response_delete,
             mk_collector_resubmit_response_t)

MKALL_NEW(MKCollectorResubmitSettings_New, mk_collector_resubmit_request_new)

MKALL_SET_LONG(MKCollectorResubmitSettings_SetTimeout,
               mk_collector_resubmit_request_set_timeout,
               mk_collector_resubmit_request_t)

MKALL_SET_STRING(MKCollectorResubmitSettings_SetCABundlePath,
                 mk_collector_resubmit_request_set_ca_bundle_path,
                 mk_collector_resubmit_request_t)

MKALL_SET_STRING(MKCollectorResubmitSettings_SetContent,
                 mk_collector_resubmit_request_set_content,
                 mk_collector_resubmit_request_t)

MKALL_GET_POINTER(MKCollectorResubmitSettings_Perform,
                  mk_collector_resubmit,
                  mk_collector_resubmit_request_t)

MKALL_DELETE(MKCollectorResubmitSettings_Delete,
             mk_collector_resubmit_request_delete,
             mk_collector_resubmit_request_t)
