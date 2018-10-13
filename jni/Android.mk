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
LOCAL_STATIC_LIBRARIES := GeoIP ssl event event_openssl \
                          event_pthreads crypto
LOCAL_MODULE := measurement_kit
include jni/mk-files.mk
LOCAL_CPPFLAGS += -I jni/$(TARGET_ARCH_ABI)/include -std=c++14                 \
                  -I jni/measurement-kit/include -Wall -Wextra                 \
                  -DHTTP_PARSER_STRICT=0                                       \
                  -DHAVE_BUFFEREVENT_OPENSSL_SET_ALLOW_DIRTY_SHUTDOWN          \
                  -DMK_CA_BUNDLE="\"\"" -I jni/measurement-kit
LOCAL_CFLAGS += -I jni/$(TARGET_ARCH_ABI)/include                              \
                -I jni/measurement-kit/include -Wall -Wextra
include $(BUILD_SHARED_LIBRARY)
