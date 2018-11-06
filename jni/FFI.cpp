// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mk-jni-api.h"
#include "mk-jni-util.hpp"

#include "measurement_kit/ffi.h"

MKJNI_NEW_WITH_STRING_ARGUMENT(Task_StartNettest, mk_nettest_start)

MKJNI_GET_BOOLEAN(Task_IsDone, mk_task_is_done, mk_task_t)

MKJNI_GET_POINTER(Task_WaitForNextEvent,
                  mk_task_wait_for_next_event,
                  mk_task_t)

MKJNI_CALL(Task_Interrupt, mk_task_interrupt, mk_task_t)

MKJNI_DELETE(Task_Destroy, mk_task_destroy, mk_task_t)

MKJNI_GET_STRING(Event_Serialize, mk_event_serialize, mk_event_t)

MKJNI_DELETE(Event_Destroy, mk_event_destroy, mk_event_t)
