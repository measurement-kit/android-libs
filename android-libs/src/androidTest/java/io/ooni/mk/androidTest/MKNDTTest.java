// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import org.junit.Test;

import androidx.test.filters.SmallTest;

import io.ooni.mk.MKAsyncTask;

@SmallTest public class MKNDTTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        String settings = "";
        {
            settings += "{\n";
            settings += "  \"name\": \"Ndt\",\n";
            settings += "  \"log_level\": \"INFO\",\n";
            settings += "  \"options\": {\n";
            settings += "    \"net/ca_bundle_path\": \"cacert.pem\",\n";
            settings += "    \"geoip_country_path\": \"country.mmdb\",\n";
            settings += "    \"geoip_asn_path\": \"asn.mmdb\"\n";
            settings += "  }\n";
            settings += "}\n";
        }
        MKAsyncTask task = MKAsyncTask.start(settings);
        while (!task.isDone()) {
            System.out.println(task.waitForNextEvent());
        }
    }
}
