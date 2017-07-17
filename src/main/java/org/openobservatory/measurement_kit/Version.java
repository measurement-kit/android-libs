// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit;

/**
 * @deprecated Will be removed in a future version. You should use the
 * namesake class in the `common` subpackage.
 */
public class Version {
    public static String getVersion() {
        return org.openobservatory.measurement_kit.swig.Version.version();
    }
}
