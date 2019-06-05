cmake_minimum_required(VERSION 3.4.1)

set(MK_ANDROID_DIST "/usr/local/opt" CACHE PATH
    "Directory where the required libraries are installed")

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wextra")

set(SRCDIR ${CMAKE_SOURCE_DIR}/src/main/cpp)

add_library(libcurl STATIC IMPORTED)
set_target_properties(libcurl PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-curl/${ANDROID_ABI}/lib/libcurl.a)

add_library(libevent_core STATIC IMPORTED)
set_target_properties(libevent_core PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-libevent/${ANDROID_ABI}/lib/libevent_core.a)

add_library(libevent_extra STATIC IMPORTED)
set_target_properties(libevent_extra PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-libevent/${ANDROID_ABI}/lib/libevent_extra.a)

add_library(libevent_openssl STATIC IMPORTED)
set_target_properties(libevent_openssl PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-libevent/${ANDROID_ABI}/lib/libevent_openssl.a)

add_library(libevent_pthreads STATIC IMPORTED)
set_target_properties(libevent_pthreads PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-libevent/${ANDROID_ABI}/lib/libevent_pthreads.a)

add_library(libmaxminddb STATIC IMPORTED)
set_target_properties(libmaxminddb PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-libmaxminddb/${ANDROID_ABI}/lib/libmaxminddb.a)

add_library(libcrypto STATIC IMPORTED)
set_target_properties(libcrypto PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-libressl/${ANDROID_ABI}/lib/libcrypto.a)

add_library(libssl STATIC IMPORTED)
set_target_properties(libssl PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-libressl/${ANDROID_ABI}/lib/libssl.a)

add_library(libmeasurement_kit_static STATIC IMPORTED)
set_target_properties(libmeasurement_kit_static PROPERTIES IMPORTED_LOCATION
  ${MK_ANDROID_DIST}/android-measurement-kit/${ANDROID_ABI}/lib/libmeasurement_kit.a)

add_library(measurement_kit SHARED ${SRCDIR}/collector.cpp ${SRCDIR}/ffi.cpp
  ${SRCDIR}/geoiplookup.cpp ${SRCDIR}/orchestra.cpp ${SRCDIR}/version.cpp)

target_include_directories(measurement_kit PRIVATE
  ${MK_ANDROID_DIST}/android-measurement-kit/${ANDROID_ABI}/include)

# Note: library order should not be changed because that breaks linking
target_link_libraries(measurement_kit android libmeasurement_kit_static
  libmaxminddb libcurl z libevent_openssl libssl libcrypto libevent_extra
  libevent_core libevent_pthreads log)
