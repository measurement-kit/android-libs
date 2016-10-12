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
LOCAL_MODULE := libmeasurement_kit
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libmeasurement_kit.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libssl
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/lib/libssl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog -latomic
# Note to self: the order of libraries matters
LOCAL_STATIC_LIBRARIES := measurement_kit GeoIP ssl crypto event event_openssl \
                          event_pthreads
LOCAL_MODULE := measurement_kit-android
LOCAL_SRC_FILES := common.cpp logger_api.cpp ooni_sync_api.cpp                 \
                   ooni_test_wrapper_wrap.cpp portolan_sync_api.cpp            \
                   saved_jvm.cpp ooni_test_wrapper_extra.cpp
APP_PLATFORM := android-21
LOCAL_CPPFLAGS += -I jni/$(TARGET_ARCH_ABI)/include -std=c++11
include $(BUILD_SHARED_LIBRARY)
