// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.ooni.mk;

public class Task {
    long handle = 0;

    final static native long StartNettest(String settings);

    final static native boolean IsDone(long handle);

    final static native long WaitForNextEvent(long handle);

    final static native void Interrupt(long handle);

    final static native void Destroy(long handle);

    Task(long n) {
        handle = n;
    }

    public static Task startNettest(String settings) {
        long handle = StartNettest(settings);
        if (handle == 0) {
          throw new RuntimeException("Task.startNettest failed");
        }
        return new Task(handle);
    }

    public boolean isDone() {
        return IsDone(handle);
    }

    public Event waitForNextEvent() {
        long event = WaitForNextEvent(handle);
        if (event == 0) {
          throw new RuntimeException("Task.WaitForNextEvent failed");
        }
        return new Event(handle);
    }

    public void interrupt() {
        Interrupt(handle);
    }

    @Override
    public synchronized void finalize() {
        Destroy(handle);
    }
}
