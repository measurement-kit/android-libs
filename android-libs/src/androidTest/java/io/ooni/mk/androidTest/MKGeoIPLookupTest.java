// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.google.common.truth.Truth;
import io.ooni.mk.MKGeoIPLookupResults;
import io.ooni.mk.MKGeoIPLookupTask;
import io.ooni.mk.MKResourcesManager;

@SmallTest public class MKGeoIPLookupTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        boolean okay = MKResourcesManager.maybeUpdateResources(ApplicationProvider.getApplicationContext());
        Truth.assertThat(okay).isTrue();
        MKGeoIPLookupTask task = new MKGeoIPLookupTask();
        task.setTimeout(14);
        task.setASNDBPath(MKResourcesManager.getASNDBPath(ApplicationProvider.getApplicationContext()));
        task.setCountryDBPath(MKResourcesManager.getCountryDBPath(ApplicationProvider.getApplicationContext()));
        task.setCABundlePath(MKResourcesManager.getCABundlePath(ApplicationProvider.getApplicationContext()));
        MKGeoIPLookupResults results = task.perform();
        System.out.print(results.getLogs());
        Truth.assertThat(results.isGood()).isTrue();
        System.out.println("Probe IP  : " + results.getProbeIP());
        System.out.println("Probe ASN : " + results.getProbeASN());
        System.out.println("Probe CC  : " + results.getProbeCC());
        System.out.println("Probe Org : " + results.getProbeOrg());
    }
}
