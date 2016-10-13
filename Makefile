PHONIES += check dist download-and-verify help javah jni-libs
PHONIES += jni-libs-no-unpack unpack unpack-clean
.PHONY: $(PHONIES)

GPG2      = gpg2
JAVAH     = javah
NDK_BUILD = /usr/local/Cellar/android-ndk/r12b/ndk-build
SWIG      = swig
WGET      = wget

BASEURL   = https://github.com/measurement-kit/measurement-kit/releases/download
VERSION   = v0.3.2
TAG       = -android_jni
INPUT     = measurement_kit-$(VERSION)$(TAG).tar.bz2
OVERSION  = $(VERSION)-2-alpha.2
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

dist: jni-libs
	@echo "Creating $(OUTPUT)..."
	@tar -cjf $(OUTPUT) java jniLibs
	@gpg2 -u 7733D95B -b --armor $(OUTPUT)

jni-libs: unpack javah run-swig jni-libs-no-unpack

run-swig:
	./scripts/run-swig

javah:
	@echo "Creating header files in jni using $(JAVAH)..."
	@cd jni && $(JAVAH) -cp ../java $(PACKAGE).sync.OoniSyncApi
	@cd jni && $(JAVAH) -cp ../java $(PACKAGE).sync.PortolanSyncApi
	@cd jni && $(JAVAH) -cp ../java $(PACKAGE).LoggerApi

jni-libs-no-unpack:
	$(NDK_BUILD) NDK_LIBS_OUT=./jniLibs

unpack: unpack-clean download-and-verify
	@echo "Unpack $(INPUT) inside jni"
	@tar xf $(INPUT)

unpack-clean:
	@echo "Cleanup jni dirs: $(ABIS)"
	@for ABI in $(ABIS); do                                                \
	  rm -rf jni/$$ABI/*;                                                  \
	done

download-and-verify: check $(INPUT) $(INPUT).asc
	$(GPG2) --verify $(INPUT).asc

check:
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
	@if [ -z "$$(which $(NDK_BUILD))" ]; then                              \
	  echo "FATAL: install $(NDK_BUILD) or make sure it's in PATH" 1>&2;   \
	  exit 1;                                                              \
	fi
	@echo "Using $(NDK_BUILD): $$(which $(NDK_BUILD))"
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
	$(WGET) $(BASEURL)/$(VERSION)/$(INPUT)

$(INPUT).asc:
	$(WGET) $(BASEURL)/$(VERSION)/$(INPUT).asc
