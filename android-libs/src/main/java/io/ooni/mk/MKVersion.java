// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

/** MKVersion allows to get version information. */
public class MKVersion {
    /** getVersionMK returns Measurement Kit version as a string. */
    public final static native String getVersionMK();
}
