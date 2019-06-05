PHONIES += all configure dist sign
.PHONY: $(PHONIES)

UPSTREAM     = `ls /usr/local/Cellar/android-measurement-kit/|tail -n1|tr '_' '-'`
OURS         = -android.2
VERSION_CODE = 45
VERSION_NAME = $(UPSTREAM)$(OURS)
OUTPUT       = android-libs-$(VERSION_NAME).aar
POM          = android-libs-$(VERSION_NAME).pom

all: dist

configure:
	./script/common/javah
	./script/android/configure $(VERSION_CODE) $(VERSION_NAME)

dist: configure
	./script/android/build
	./script/android/archive $(OUTPUT) $(POM) $(VERSION_NAME)

sign: dist
	./script/android/sign $(OUTPUT) $(POM)
