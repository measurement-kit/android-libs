PHONIES += check dist download-and-verify help jni-libs
PHONIES += recompile redist unpack unpack-clean
.PHONY: $(PHONIES)

GIT       = git
GPG2      = gpg
NDK_BUILD = # Empty: must be provided on the command line
SWIG      = swig
WGET      = wget

INPUT     = android-dependencies-20170714T172439Z.tgz
DEPS_URL  = https://github.com/measurement-kit/dependencies/releases/download/2017-07-12/$(INPUT)
VERSION   = 0.7.6
BRANCH_OR_TAG = v$(VERSION)
OVERSION  = $(VERSION)-1
OUTPUT    = android-libs-$(OVERSION).aar
POM       = android-libs-$(OVERSION).pom
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
	@ANDROID_HOME=$$(dirname $$(dirname $(NDK_BUILD))) ./gradlew :assembleDebug
	@cp build/outputs/aar/android-libs-debug.aar $(OUTPUT)
	@$(GPG2) -u 738877AA6C829F26A431C5F480B691277733D95B                   \
	         -b --armor $(OUTPUT)
	@cat template.pom | sed 's/@VERSION@/$(OVERSION)/g' > $(POM)
	@$(GPG2) -u 738877AA6C829F26A431C5F480B691277733D95B                   \
	         -b --armor $(POM)

jni-libs: unpack recompile

recompile:
	$(NDK_BUILD) NDK_LIBS_OUT=./src/main/jniLibs

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
	$(GIT) clone --single-branch --depth 1 --branch $(BRANCH_OR_TAG)       \
	  https://github.com/measurement-kit/measurement-kit.git               \
	  jni/measurement-kit
	rm -rf -- jni/mk-files.mk
	./scripts/m4
	./scripts/swig
	cd jni/measurement-kit && ./autogen.sh -n
	for NAME in                                                            \
	      `cd jni && find measurement-kit/src/libmeasurement_kit wrappers  \
	        -type f \( -name \*.c -o -name \*.cpp \)`; do                  \
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
