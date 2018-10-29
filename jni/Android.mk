LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libGeoIP
LOCAL_SRC_FILES := ../MK_DIST/android/geoip-api-c/1.6.12-9/$(TARGET_ARCH_ABI)/lib/libGeoIP.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libcrypto
LOCAL_SRC_FILES := ../MK_DIST/android/libressl/2.7.4-9/$(TARGET_ARCH_ABI)/lib/libcrypto.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent
LOCAL_SRC_FILES := ../MK_DIST/android/libevent/2.1.8-9/$(TARGET_ARCH_ABI)/lib/libevent.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent_openssl
LOCAL_SRC_FILES := ../MK_DIST/android/libevent/2.1.8-9/$(TARGET_ARCH_ABI)/lib/libevent_openssl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent_pthreads
LOCAL_SRC_FILES := ../MK_DIST/android/libevent/2.1.8-9/$(TARGET_ARCH_ABI)/lib/libevent_pthreads.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libssl
LOCAL_SRC_FILES := ../MK_DIST/android/libressl/2.7.4-9/$(TARGET_ARCH_ABI)/lib/libssl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libcurl
LOCAL_SRC_FILES := ../MK_DIST/android/curl/7.61.1-9/$(TARGET_ARCH_ABI)/lib/libcurl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libmaxminddb
LOCAL_SRC_FILES := ../MK_DIST/android/libmaxminddb/1.3.2-9/$(TARGET_ARCH_ABI)/lib/libmaxminddb.a
include $(PREBUILT_STATIC_LIBRARY)

MK_DIR=../MK_DIST/android/measurement-kit/0.9.0-alpha.11-9/$(TARGET_ARCH_ABI)/

include $(CLEAR_VARS)
LOCAL_MODULE := libmeasurement_kit_static # another name to avoid conflict
LOCAL_SRC_FILES := $(MK_DIR)/lib/libmeasurement_kit.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog -latomic
# Note to self: the order of libraries matters
LOCAL_STATIC_LIBRARIES := measurement_kit_static libmaxminddb libcurl          \
                          GeoIP event_openssl ssl crypto event event_pthreads
LOCAL_MODULE := measurement_kit
LOCAL_CPPFLAGS += -I jni/$(MK_DIR)/include                                     \
                  -std=c++14                                                   \
                  -Wall                                                        \
                  -Wextra -DMK_NETTESTS_INTERNAL
LOCAL_SRC_FILES += wrappers/common_wrap.cpp wrappers/environment_.cpp          \
                   wrappers/nettests_wrap.cpp wrappers/ooni_wrap.cpp           \
                   wrappers/FFI.cpp wrappers/MKGeoIPLookup.cpp
include $(BUILD_SHARED_LIBRARY)
