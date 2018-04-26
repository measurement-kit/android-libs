PHONIES += dist
.PHONY: $(PHONIES)

GPG2      = gpg

VERSION   = 0.9.0-alpha
OVERSION  = $(VERSION)-1
OUTPUT    = android-libs-$(OVERSION).aar
POM       = android-libs-$(OVERSION).pom

dist:
	./scripts/m4
	./scripts/swig
	./scripts/android/download
	./scripts/android/build
	./scripts/android/archive $(OUTPUT) $(POM) $(OVERSION)
