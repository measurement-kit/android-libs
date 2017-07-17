// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.swig;

public interface OrchestrateUpdateCallback {
    public void callback(final Error error, final OrchestrateAuth auth);
}
