// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.ooni.mk;

public class FFI {
    public final static native long mk_nettest_start(String settings);
    public final static native boolean mk_nettest_is_done(long nt);
    public final static native long mk_nettest_wait_for_next_event(long nt);
    public final static native void mk_nettest_interrupt(long nt);
    public final static native void mk_nettest_destroy(long nt);
    public final static native String mk_event_serialize(long event);
    public final static native void mk_event_destroy(long event);
}
