// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import com.google.common.truth.Truth;

import org.junit.Test;

import androidx.test.filters.SmallTest;

import io.ooni.mk.MKVersion;

@SmallTest public class MKVersionTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        String version = MKVersion.getVersionMK();
        Truth.assertThat(version).isNotNull();
        Truth.assertThat(version.length()).isGreaterThan(0);
    }
}
