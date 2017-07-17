// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.swig;

public interface OrchestrateFindLocationCallback {
    public void callback(final Error error, final String probe_asn,
			 final String probe_cc);
}
