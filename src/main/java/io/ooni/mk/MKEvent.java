// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

class MKEvent {
    long handle = 0;

    final static native String Serialize(long handle);

    final static native void Destroy(long handle);

    protected MKEvent(long n) {
        handle = n;
    }

    public String serialize() {
        return Serialize(handle);
    }

    @Override
    public synchronized void finalize() {
        Destroy(handle);
    }
}
