// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.ooni.mk;

import io.ooni.mk.FFI;

public class Task {
    public static Task startNettest(String conf) {
        long task = FFI.mk_nettest_start(conf);
        return (task != 0) ? new Task(task) : null;
    }

    public boolean isDone() {
        return FFI.mk_task_is_done(handle);
    }

    public Event waitForNextEvent() {
        long event = FFI.mk_task_wait_for_next_event(handle);
        return (event != 0) ? new Event(event) : null;
    }

    public void interrupt() {
        FFI.mk_task_interrupt(handle);
    }

    @Override
    public synchronized void finalize() {
        FFI.mk_task_destroy(handle);
        handle = 0;
    }

    private Task(long handle) {
        this.handle = handle;
    }

    private long handle = 0;
}
