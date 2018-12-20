PHONIES += dist
.PHONY: $(PHONIES)

UPSTREAM     = 0.9.0-beta
OURS         = 4
VERSION_CODE = 34
VERSION_NAME = $(UPSTREAM)-android.$(OURS)
OUTPUT       = android-libs-$(VERSION_NAME).aar
POM          = android-libs-$(VERSION_NAME).pom

dist:
	./script/common/copy-sources
	./script/common/javah
	./script/android/download
	./script/android/configure $(VERSION_CODE) $(VERSION_NAME)
	./script/android/build
	./script/android/archive $(OUTPUT) $(POM) $(VERSION_NAME)
