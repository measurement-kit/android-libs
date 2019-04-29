// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

/** MKTask is a task that Measurement Kit is running. */
public class MKTask {
    long handle = 0;

    final static native long Start(String settings);

    final static native boolean IsDone(long handle);

    final static native long WaitForNextEvent(long handle);

    final static native void Interrupt(long handle);

    final static native void Destroy(long handle);

    MKTask(long n) {
        handle = n;
    }

    /** start starts a new task with the specified settings that must
     * be a valid serialized JSON string. */
    public static MKTask start(String settings) {
        long handle = Start(settings);
        if (handle == 0) {
            throw new RuntimeException("MKTask.start failed");
        }
        return new MKTask(handle);
    }

    /** isDone returns true when the task is done. */
    public boolean isDone() {
        return IsDone(handle);
    }

    /** waitForNextEvent blocks until the task generates the next event
     * and then returns such events as a serialized JSON. */
    public String waitForNextEvent() {
        long event = WaitForNextEvent(handle);
        if (event == 0) {
            throw new RuntimeException("MKTask.WaitForNextEvent failed");
        }
        return new MKEvent(event).serialize();
    }

    /** interrupt interrupts the task. */
    public void interrupt() {
        Interrupt(handle);
    }

    @Override
    public synchronized void finalize() {
        Destroy(handle);
    }
}
