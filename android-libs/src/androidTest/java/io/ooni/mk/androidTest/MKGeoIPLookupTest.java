// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import org.junit.Test;

import androidx.test.filters.SmallTest;

import io.ooni.mk.MKGeoIPLookupResults;
import io.ooni.mk.MKGeoIPLookupTask;

@SmallTest public class MKGeoIPLookupTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        MKGeoIPLookupTask task = new MKGeoIPLookupTask();
        task.setTimeout(14);
        task.setCABundlePath("cacert.pem");
        task.setCountryDBPath("country.mmdb");
        task.setASNDBPath("asn.mmdb");
        MKGeoIPLookupResults results = task.perform();
        System.out.println("Good      : " + results.isGood());
        System.out.println("Probe IP  : " + results.getProbeIP());
        System.out.println("Probe ASN : " + results.getProbeASN());
        System.out.println("Probe CC  : " + results.getProbeCC());
        System.out.println("Probe Org : " + results.getProbeOrg());
        System.out.print(results.getLogs());
    }
}
