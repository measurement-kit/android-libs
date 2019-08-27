// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import com.google.common.truth.Truth;
import org.junit.Test;

import io.ooni.mk.MKGeoIPLookupResults;
import io.ooni.mk.MKGeoIPLookupTask;
import io.ooni.mk.MKResourcesManager;

@SmallTest public class MKGeoIPLookupTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        Context context = ApplicationProvider.getApplicationContext();
        boolean okay = MKResourcesManager.maybeUpdateResources(context);
        Truth.assertThat(okay).isTrue();
        MKGeoIPLookupTask task = new MKGeoIPLookupTask();
        task.setTimeout(14);
        task.setASNDBPath(MKResourcesManager.getASNDBPath(context));
        task.setCountryDBPath(MKResourcesManager.getCountryDBPath(context));
        task.setCABundlePath(MKResourcesManager.getCABundlePath(context));
        MKGeoIPLookupResults results = task.perform();
        System.out.print(results.getLogs());
        System.out.println("Probe IP  : " + results.getProbeIP());
        System.out.println("Probe ASN : " + results.getProbeASN());
        System.out.println("Probe CC  : " + results.getProbeCC());
        System.out.println("Probe Org : " + results.getProbeOrg());
        Truth.assertThat(results.isGood()).isTrue();
    }
}
