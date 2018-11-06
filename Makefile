PHONIES += dist
.PHONY: $(PHONIES)

GPG2      = gpg

VERSION   = 0.9.0-beta
OVERSION  = $(VERSION)-android+1
OUTPUT    = android-libs-$(OVERSION).aar
POM       = android-libs-$(OVERSION).pom

dist:
	./script/common/javah
	./script/android/download
	./script/android/configure
	./script/android/build
	./script/android/archive $(OUTPUT) $(POM) $(OVERSION)
