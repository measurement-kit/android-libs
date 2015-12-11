LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libevent.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent_pthreads
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libevent_pthreads.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libjansson
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libjansson.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libmeasurement_kit
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libmeasurement_kit.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libyaml-cpp
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libyaml-cpp.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog -latomic
LOCAL_STATIC_LIBRARIES := measurement_kit event event_pthreads jansson \
                          maxminddb yaml-cpp
LOCAL_MODULE := measurement_kit_jni
LOCAL_SRC_FILES := common.cpp dns_api.cpp logger_api.cpp ooni_sync_api.cpp \
                   portolan_sync_api.cpp
APP_PLATFORM := android-21
LOCAL_CPPFLAGS += -I jni/$(TARGET_ARCH_ABI)/include -std=c++11
include $(BUILD_SHARED_LIBRARY)
