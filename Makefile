PHONIES += dist
.PHONY: $(PHONIES)

UPSTREAM     = 0.9.0-beta
OURS         = 2
VERSION_CODE = 32
VERSION_NAME = $(UPSTREAM)-android.$(OURS)+$(VERSION_CODE)
OUTPUT       = android-libs-$(VERSION_NAME).aar
POM          = android-libs-$(VERSION_NAME).pom

dist:
	./script/common/javah
	./script/android/download
	./script/android/configure $(VERSION_CODE) $(VERSION_NAME)
	./script/android/build
	./script/android/archive $(OUTPUT) $(POM) $(VERSION_NAME)
