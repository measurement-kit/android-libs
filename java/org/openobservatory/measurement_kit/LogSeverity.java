// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit;

public interface LogSeverity {
    // Note: keep in sync with <measurement_kit/common/logger.hpp>

    // The numbers [0-31] are reserved for verbosity levels. Numbers above 31
    // instead are reserved for other, not yet specified semantics.
    public static final int WARNING = 0;
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int DEBUG2 = 3;
    public static final int VERBOSITY_MASK = 31;

    public static final int JSON = 32; // Information encoded as JSON
}
