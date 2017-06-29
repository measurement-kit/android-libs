# File to build shared library that can be loaded from the JDK, as opposed
# to loading the library inside Android. MacOS only (and ghetto for now).
#
# Assumes that you installed MK under /usr/local/Cellar/measurement-kit/HEAD.
#
# See: http://www.developer.com/java/data/getting-started-with-jni.html

.PHONY: all

PACKAGE = src/main/java/org/openobservatory/measurement_kit/swig

all:
	./scripts/geoip
	@echo ""
	./scripts/m4
	@echo ""
	./scripts/swig
	@echo ""
	./scripts/macos
	@echo ""
	./scripts/javac $(PACKAGE)/*.java
	@echo ""
	./scripts/javac example/java/*.java
	@echo ""
	@echo "Now you can run examples like:"
	@echo "./script/run HttpInvalidRequestLineExample"
