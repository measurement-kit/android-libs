// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.common;

public class Version {
    public static String version() {
        return org.openobservatory.measurement_kit.swig.Version.version();
    }
}
