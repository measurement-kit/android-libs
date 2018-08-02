# File to build shared library that can be loaded from the JDK, as opposed
# to loading the library inside Android. Ubuntu 17.04 only.
#
# Assumes that you have created the debian package for MK and you have
# installed it locally on the system where you are building.
#
# See: http://www.developer.com/java/data/getting-started-with-jni.html

.PHONY: all

PACKAGE = src/main/java/org/openobservatory/measurement_kit/swig

all:
	./script/geoip
	@echo ""
	./script/m4
	@echo ""
	./script/swig
	@echo ""
	./script/zesty-gcc
	@echo ""
	./script/javac $(PACKAGE)/*.java
	@echo ""
	./script/javac example/java/*.java
	@echo ""
	@echo "Now you can run examples like:"
	@echo "./script/run HttpInvalidRequestLineExample"
