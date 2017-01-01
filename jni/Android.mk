LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libGeoIP
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libGeoIP.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libcrypto
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libcrypto.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libevent.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent_openssl
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libevent_openssl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent_pthreads
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libevent_pthreads.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libssl
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libssl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog -latomic
# Note to self: the order of libraries matters
LOCAL_STATIC_LIBRARIES := GeoIP ssl crypto event event_openssl \
                          event_pthreads
LOCAL_MODULE := measurement_kit
LOCAL_SRC_FILES := wrappers/common.cpp wrappers/logger_api.cpp                 \
                   wrappers/ooni_sync_api.cpp                                  \
		   wrappers/ooni_test_wrapper_wrap.cpp                         \
		   wrappers/portolan_sync_api.cpp                              \
                   wrappers/saved_jvm.cpp                                      \
		   wrappers/ooni_test_wrapper_extra.cpp
include jni/mk-files.mk
LOCAL_CPPFLAGS += -I jni/$(TARGET_ARCH_ABI)/include -std=c++11                 \
                  -I jni/measurement-kit/include -Wall -Wextra
LOCAL_CFLAGS += -I jni/$(TARGET_ARCH_ABI)/include                              \
                -I jni/measurement-kit/include -Wall -Wextra
include $(BUILD_SHARED_LIBRARY)
