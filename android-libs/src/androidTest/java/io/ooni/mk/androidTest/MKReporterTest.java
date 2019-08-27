// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import com.google.common.truth.Truth;
import org.junit.Test;

import io.ooni.mk.MKReporterResults;
import io.ooni.mk.MKReporterTask;
import io.ooni.mk.MKResourcesManager;

@SmallTest public class MKReporterTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    static private String baseMeasurement = "{\"data_format_version\":\"0.2.0\",\"id\":\"9858087b-15b9-4913-a87e-2520f85393b2\",\"input\":null,\"input_hashes\":[],\"measurement_start_time\":\"2019-07-26 10:39:09\",\"options\":[],\"probe_asn\":\"AS0\",\"probe_cc\":\"ZZ\",\"probe_city\":null,\"probe_ip\":\"127.0.0.1\",\"report_id\":\"20190726T103909Z_AS0_KTgS3f6IgfdSuCoqQE1A31u4AtjpbO6Ywj0LPkn7c4mTd1lBut\",\"software_name\":\"measurement_kit\",\"software_version\":\"0.10.4\",\"test_helpers\":{\"backend\":\"http://37.218.247.95:80\"},\"test_keys\":{},\"test_name\":\"dummy\",\"test_runtime\":0.07317614555358887,\"test_start_time\":\"2019-07-26 10:39:05\",\"test_version\":\"0.0.1\"}";

    private MKReporterTask initialize() {
        Context context = ApplicationProvider.getApplicationContext();
        boolean okay = MKResourcesManager.maybeUpdateResources(context);
        Truth.assertThat(okay).isTrue();
        return new MKReporterTask("android-libs", "0.1.0",
                MKResourcesManager.getCABundlePath(context));
    }

    private MKReporterResults submit(MKReporterTask task, String measurement) {
        long timeout = 300;
        MKReporterResults results = task.maybeDiscoverAndSubmit(measurement, timeout);
        System.out.print(results.getLogs());
        System.out.println("updatedMeasurement  : " + results.getUpdatedSerializedMeasurement());
        System.out.println("updatedReportID     : " + results.getUpdatedReportID());
        System.out.println("serializedStats     : " + results.getSerializedStats());
        Truth.assertThat(results.isGood()).isTrue();
        return results;
    }

    @Test public void submitOne() {
        submit(initialize(), baseMeasurement);
    }

    @Test public void submitMany() {
        MKReporterTask task = initialize();
        String m = baseMeasurement.replace("\"input\":null", "\"input\":\"a\"");
        submit(task, m);
        m = baseMeasurement.replace("\"input\":null", "\"input\":\"b\"");
        submit(task, m);
        m = baseMeasurement.replace("\"test_name\":\"dummy\"",
                                    "\"test_name\":\"x\"");
        submit(task, m);
    }
}
