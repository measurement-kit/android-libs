PHONIES += all configure dist sign
.PHONY: $(PHONIES)

# Fields to change at every release
EXPECTED_VER   = 0.10.11-1
VERSION_CODE   = 60
VERSION_NAME   = 0.11.0-1

CELLAR         = /usr/local/Cellar
GENERIC_ASSETS = /usr/local/opt/generic-assets
UPSTREAM       = `ls $(CELLAR)/android-measurement-kit/|tail -n1|tr '_' '-'`
OUTPUT         = android-libs-$(VERSION_NAME).aar
POM            = android-libs-$(VERSION_NAME).pom

all: dist

check:
	test "$(UPSTREAM)" = "$(EXPECTED_VER)"

configure: check
	./script/common/javah
	./script/android/configure $(VERSION_CODE) $(VERSION_NAME) $(GENERIC_ASSETS)

dist: configure
	./script/android/build
	./script/android/archive $(OUTPUT) $(POM) $(VERSION_NAME)

sign: dist
	./script/android/sign $(OUTPUT) $(POM)
