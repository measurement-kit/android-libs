LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libGeoIP
LOCAL_SRC_FILES := measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/geoip/lib/libGeoIP.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libcrypto
LOCAL_SRC_FILES := measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libressl/lib/libcrypto.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent
LOCAL_SRC_FILES := measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libevent/lib/libevent.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent_openssl
LOCAL_SRC_FILES := measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libevent/lib/libevent_openssl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libevent_pthreads
LOCAL_SRC_FILES := measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libevent/lib/libevent_pthreads.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libssl
LOCAL_SRC_FILES := measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libressl/lib/libssl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog -latomic
# Note to self: the order of libraries matters
LOCAL_STATIC_LIBRARIES := GeoIP ssl crypto event event_openssl \
                          event_pthreads
LOCAL_MODULE := measurement_kit
include jni/mk-files.mk
LOCAL_CPPFLAGS +=                                                              \
  -I jni/measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/geoip/include      \
  -I jni/measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libevent/include   \
  -I jni/measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libressl/include   \
  -std=c++14 -I jni/measurement-kit/include -Wall -Wextra                      \
  -I jni/measurement-kit -DHTTP_PARSER_STRICT=0                                \
  -DHAVE_BUFFEREVENT_OPENSSL_SET_ALLOW_DIRTY_SHUTDOWN -DMK_CA_BUNDLE="\"\""
LOCAL_CFLAGS +=                                                                \
  -I jni/measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/geoip/include      \
  -I jni/measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libevent/include   \
  -I jni/measurement-kit/MK_DIST/android/$(TARGET_ARCH_ABI)/libressl/include   \
  -I jni/measurement-kit/include -Wall -Wextra -I jni/measurement-kit
include $(BUILD_SHARED_LIBRARY)
