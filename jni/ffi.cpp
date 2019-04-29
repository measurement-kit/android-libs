// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mkall.h"

#include <limits.h>

#include <measurement_kit/ffi.h>

#include "mkall_util.h"

MKALL_NEW_WITH_STRING_ARGUMENT(MKTask_Start, mk_task_start)

MKALL_GET_BOOLEAN(MKTask_IsDone, mk_task_is_done, mk_task_t)

MKALL_GET_POINTER(MKTask_WaitForNextEvent,
                  mk_task_wait_for_next_event,
                  mk_task_t)

MKALL_CALL(MKTask_Interrupt, mk_task_interrupt, mk_task_t)

MKALL_DELETE(MKTask_Destroy, mk_task_destroy, mk_task_t)

MKALL_GET_STRING(MKEvent_Serialize, mk_event_serialize, mk_event_t)

MKALL_DELETE(MKEvent_Destroy, mk_event_destroy, mk_event_t)
