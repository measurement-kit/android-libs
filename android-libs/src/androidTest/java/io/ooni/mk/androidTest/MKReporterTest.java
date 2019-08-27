// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import com.google.common.truth.Truth;
import com.google.gson.Gson;
import org.junit.Test;

import io.ooni.mk.MKReporterResults;
import io.ooni.mk.MKReporterTask;
import io.ooni.mk.MKResourcesManager;

@SmallTest public class MKReporterTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    static final String softwareName = "android-libs";
    static final String softwareVersion = "0.1.0";
    static final String testName = "dummy";
    static final String testVersion = "0.1.0";
    static final Gson gson = new Gson();

    private MKReporterTask initialize() {
        Context context = ApplicationProvider.getApplicationContext();
        boolean okay = MKResourcesManager.maybeUpdateResources(context);
        Truth.assertThat(okay).isTrue();
        return new MKReporterTask(softwareName, softwareVersion,
                MKResourcesManager.getCABundlePath(context));
    }

    private MKReporterResults submit(MKReporterTask task, MKTMeasurement measurement) {
        final long timeout = 300;
        MKReporterResults results = task.maybeDiscoverAndSubmit(
            gson.toJson(measurement), timeout
        );
        System.out.print(results.getLogs());
        System.out.println("updatedMeasurement  : " + results.getUpdatedSerializedMeasurement());
        System.out.println("updatedReportID     : " + results.getUpdatedReportID());
        System.out.println("serializedStats     : " + results.getSerializedStats());
        Truth.assertThat(results.isGood()).isTrue();
        Truth.assertThat(results.getUpdatedReportID().length()).isGreaterThan(0);
        return results;
    }

    @Test public void submitOne() {
        submit(initialize(), new MKTMeasurement(
            testName, testVersion, "", ""
        ));
    }

    @Test public void submitMany() {
        MKReporterTask task = initialize();
        submit(task, new MKTMeasurement(
            testName, testVersion, "a", ""
        ));
        submit(task, new MKTMeasurement(
            testName, testVersion, "b", ""
        ));
        submit(task, new MKTMeasurement(
            testName + "x", testVersion, "c", ""
        ));
    }
}
