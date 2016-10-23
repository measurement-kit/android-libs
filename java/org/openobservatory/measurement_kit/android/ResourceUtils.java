// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.android;

import android.app.Activity;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;

public class ResourceUtils {
    private static final String LOG_TAG = "ResourceUtils";
    private static final String CA_BUNDLE = "cacert.pem";
    private static final String GEOIP = "geoip.dat";
    private static final String GEOIP_ASNUM = "geoipasnum.dat";

    public static void copy_ca_bundle(Activity activity, int id) {
        copy_resource(activity, id, CA_BUNDLE);
    }

    public static void copy_geoip(Activity activity, int id) {
        copy_resource(activity, id, GEOIP);
    }

    public static void copy_geoip_asnum(Activity activity, int id) {
        copy_resource(activity, id, GEOIP_ASNUM);
    }

    public static void copy_resource(Activity activity, int id, String filename) {
        // Note to self: I am not totally sure this code handles correctly
        // exceptions and resources cleanup under read/write errrors
        Log.v(LOG_TAG, "copy_resource...");
        try (InputStream in = activity.getResources().openRawResource(id)) {
            try (FileOutputStream out = activity.openFileOutput(filename, 0)) {
                byte[] buff = new byte[1024];
                int count;
                while ((count = in.read(buff)) > 0) {
                    out.write(buff, 0, count);
                }
            } catch (Exception err) {
                Log.e(LOG_TAG, "error opening output file");
                // FALLTHROUGH
            }
        } catch (Exception err) {
            Log.e(LOG_TAG, "error getting resource");
            // FALLTHROUGH
        }
        Log.v(LOG_TAG, "copy_resource... done");
    }

    public static String get_ca_bundle_path(Activity activity) {
        return get_path(activity, CA_BUNDLE);
    }

    public static String get_geoip_path(Activity activity) {
        return get_path(activity, GEOIP);
    }

    public static String get_geoip_asnum_path(Activity activity) {
        return get_path(activity, GEOIP_ASNUM);
    }

    public static String get_path(Activity activity, String string) {
        return activity.getFilesDir() + "/" + string;
    }
}
