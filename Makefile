PHONIES += dist
.PHONY: $(PHONIES)

GPG2      = gpg

VERSION   = 0.9.0-alpha.8-1
OVERSION  = $(VERSION)-android.1
OUTPUT    = android-libs-$(OVERSION).aar
POM       = android-libs-$(OVERSION).pom

dist:
	./script/m4
	./script/swig
	./script/android/download
	./script/android/build
	./script/android/archive $(OUTPUT) $(POM) $(OVERSION)
