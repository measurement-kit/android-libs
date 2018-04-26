PHONIES += dist help
.PHONY: $(PHONIES)

GPG2      = gpg

VERSION   = 0.9.0-alpha
OVERSION  = $(VERSION)-1
OUTPUT    = android-libs-$(OVERSION).aar
POM       = android-libs-$(OVERSION).pom

help:
	@printf "Targets:\n"
	@for TARGET in `grep ^PHONIES Makefile|sed 's/^PHONIES += //'`; do     \
	  if echo $$TARGET|grep -qv ^_; then                                   \
	    printf "  - $$TARGET\n";                                           \
	  fi;                                                                  \
	done

dist:
	./scripts/m4
	./scripts/swig
	./scripts/android/download
	./scripts/android/build
	./scripts/android/archive $(OUTPUT) $(POM)
