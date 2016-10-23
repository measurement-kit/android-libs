PHONIES += check dist download-and-verify help javah jni-libs
PHONIES += recompile redist unpack unpack-clean
.PHONY: $(PHONIES)

GIT       = git
GPG2      = gpg2
JAVAH     = javah
NDK_BUILD = # Empty: must be provided on the command line
SWIG      = swig
WGET      = wget

INPUT     = android-dependencies-20161003T225928Z.tgz
DEPS_URL  = https://github.com/measurement-kit/measurement-kit-deps/releases/download/2016-10-03/$(INPUT)
VERSION   = v0.3.2
OVERSION  = $(VERSION)-2-alpha.5
OUTPUT    = measurement_kit_android-$(OVERSION).tar.bz2
PACKAGE   = org.openobservatory.measurement_kit

ABIS      = arm64-v8a armeabi armeabi-v7a mips mips64 x86 x86_64

help:
	@printf "Targets:\n"
	@for TARGET in `grep ^PHONIES Makefile|sed 's/^PHONIES += //'`; do     \
	  if echo $$TARGET|grep -qv ^_; then                                   \
	    printf "  - $$TARGET\n";                                           \
	  fi;                                                                  \
	done

dist: jni-libs redist

redist: recompile
	@echo "Creating $(OUTPUT)..."
	@tar -cjf $(OUTPUT) java jniLibs
	@$(GPG2) -u 738877AA6C829F26A431C5F480B691277733D95B                   \
	         -b --armor $(OUTPUT)

jni-libs: unpack javah run-swig recompile

run-swig:
	./scripts/run-swig

javah:
	@echo "Creating header files in jni using $(JAVAH)..."
	@cd jni/wrappers && $(JAVAH) -cp ../../java $(PACKAGE).sync.OoniSyncApi
	@cd jni/wrappers && $(JAVAH) -cp ../../java $(PACKAGE).sync.PortolanSyncApi
	@cd jni/wrappers && $(JAVAH) -cp ../../java $(PACKAGE).LoggerApi

recompile:
	$(NDK_BUILD) NDK_LIBS_OUT=./jniLibs

unpack: unpack-clean download-and-verify clone-mk
	@echo "Unpack $(INPUT) inside jni"
	@tar xf $(INPUT)

unpack-clean:
	@echo "Cleanup jni dirs: $(ABIS)"
	@for ABI in $(ABIS); do                                                \
	  rm -rf jni/$$ABI/*;                                                  \
	done

download-and-verify: check $(INPUT) $(INPUT).asc
	$(GPG2) --verify $(INPUT).asc

clone-mk: check
	rm -rf -- jni/measurement-kit
	$(GIT) clone --single-branch --depth 1 --branch $(VERSION)             \
	  https://github.com/measurement-kit/measurement-kit.git               \
	  jni/measurement-kit
	rm -rf -- jni/mk-files.mk
	for NAME in                                                            \
	      `cd jni && find measurement-kit/src/libmeasurement_kit -type f   \
	        \( -name \*.c -o -name \*.cpp \)`; do                          \
	    echo "LOCAL_SRC_FILES += $$NAME" >> jni/mk-files.mk;               \
	done

check:
	@if [ -z "$$(which $(GIT))" ]; then                                    \
	  echo "FATAL: install $(GIT) or make sure it's in PATH" 1>&2;         \
	  exit 1;                                                              \
	fi
	@echo "Using $(GIT): $$(which $(GIT))"
	@if [ -z "$$(which $(GPG2))" ]; then                                   \
	  echo "FATAL: install $(GPG2) or make sure it's in PATH" 1>&2;        \
	  exit 1;                                                              \
	fi
	@echo "Using $(GPG2): $$(which $(GPG2))"
	@if [ -z "$$(which $(JAVAH))" ]; then                                  \
	  echo "FATAL: install $(JAVAH) or make sure it's in PATH" 1>&2;       \
	  exit 1;                                                              \
	fi
	@echo "Using $(JAVAH): $$(which $(JAVAH))"
	@if [ -z "$(NDK_BUILD)" ]; then                                        \
	  echo "FATAL: You should provide path to NDK_BUILD yourself" 1>&2;    \
	  echo  "E.g., make dist NDK_BUILD=~/Library/Android/sdk/ndk-bundle/ndk-build" 1>&2; \
	  exit 1;                                                              \
	fi
	@echo "Using ndk-build: $(NDK_BUILD)"
	@if [ -z "$$(which $(SWIG))" ]; then                                   \
	  echo "FATAL: install $(SWIG) or make sure it's in PATH" 1>&2;        \
	  exit 1;                                                              \
	fi
	@echo "Using $(SWIG): $$(which $(SWIG))"
	@if [ -z "$$(which $(WGET))" ]; then                                   \
	  echo "FATAL: install $(WGET) or make sure it's in PATH" 1>&2;        \
	  exit 1;                                                              \
	fi
	@echo "Using $(WGET): $$(which $(WGET))"

$(INPUT):
	$(WGET) $(DEPS_URL)

$(INPUT).asc:
	$(WGET) $(DEPS_URL).asc
