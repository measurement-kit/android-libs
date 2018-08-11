// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.ooni.mk;

public class FFI {
    public final static native long mk_nettest_start(String settings);
    public final static native boolean mk_task_is_done(long task);
    public final static native long mk_task_wait_for_next_event(long task);
    public final static native void mk_task_interrupt(long task);
    public final static native void mk_task_destroy(long task);
    public final static native String mk_event_serialize(long event);
    public final static native void mk_event_destroy(long event);
}
