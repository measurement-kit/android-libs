PHONIES += dist
.PHONY: $(PHONIES)

UPSTREAM     = 0.10.3-1
OURS         = -android.1
VERSION_CODE = 46
VERSION_NAME = $(UPSTREAM)$(OURS)
OUTPUT       = android-libs-$(VERSION_NAME).aar
POM          = android-libs-$(VERSION_NAME).pom

dist:
	./script/common/copy-sources
	./script/common/javah
	./script/android/download
	./script/android/configure $(VERSION_CODE) $(VERSION_NAME)
	./script/android/build
	./script/android/archive $(OUTPUT) $(POM) $(VERSION_NAME)

sign: dist
	./script/android/sign $(OUTPUT) $(POM)
