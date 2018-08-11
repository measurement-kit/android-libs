// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.ooni.mk;

import io.ooni.mk.FFI;

public class Event {
    public String serialize() {
        return FFI.mk_event_serialize(handle);
    }

    @Override
    public synchronized void finalize() {
        FFI.mk_event_destroy(handle);
        handle = 0;
    }

    protected Event(long handle) {
        this.handle = handle;
    }

    private long handle = 0;
}
