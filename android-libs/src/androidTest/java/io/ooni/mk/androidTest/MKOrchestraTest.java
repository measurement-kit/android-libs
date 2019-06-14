// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import org.junit.Test;

import androidx.test.filters.SmallTest;

import io.ooni.mk.MKOrchestraResults;
import io.ooni.mk.MKOrchestraTask;

@SmallTest public class MKOrchestraTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        MKOrchestraTask task = new MKOrchestraTask();
        task.setAvailableBandwidth("10110111");
        task.setDeviceToken("5f2c761f-2e98-43aa-b9ea-3d34cceaab15");
        task.setCABundlePath("cacert.pem");
        task.setGeoIPCountryPath("country.mmdb");
        task.setGeoIPASNPath("asn.mmdb");
        task.setLanguage("it_IT");
        task.setNetworkType("wifi");
        task.setPlatform("macos");
        // Disabled so that the library will need to guess them
        //task.setProbeASN("AS30722");
        //task.setProbeCC("IT");
        task.setProbeFamily("sbs");
        task.setProbeTimezone("Europe/Rome");
        task.setRegistryURL("https://registry.proteus.test.ooni.io");
        task.setSecretsFile(".orchestra.json");
        task.setSoftwareName("mkall-java");
        task.setSoftwareVersion("0.1.0");
        task.addSupportedTest("web_connectivity");
        task.addSupportedTest("ndt");
        task.setTimeout(14);
        {
            MKOrchestraResults result = task.updateOrRegister();
            System.out.println("Good      : " + result.isGood());
            System.out.print(result.getLogs());
        }
        task.setNetworkType("mobile");
        {
            MKOrchestraResults result = task.updateOrRegister();
            System.out.println("Good      : " + result.isGood());
            System.out.print(result.getLogs());
        }
    }
}