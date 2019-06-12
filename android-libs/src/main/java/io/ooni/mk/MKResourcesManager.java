// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

// MKResourcesManager allows to keep resources required by C/C++ code up to date. The policy used
// to update resources is described in the documentation of maybeUpdateResources.
public class MKResourcesManager {
    static final String ASN_DB = "asn.mmdb";
    static final String CA_BUNDLE = "cabundle.pem";
    static final String COUNTRY_DB = "country.mmdb";
    static final String LOG_TAG = "MKResourcesManager";
    static final String VERSION_JSON = "version.json";

    static void copy(@NonNull InputStream source, @NonNull String dest) throws java.io.IOException {
        org.apache.commons.io.FileUtils.copyToFile(source, new File(dest));
    }

    static boolean copySingleResource(@NonNull Context context, @NonNull String filename) {
        String destPath = getPath(context, filename);
        String logMsg = "Copy resource " + filename + " to " + destPath;
        Log.v(LOG_TAG, logMsg + "...");
        try {
            copy(context.getAssets().open(filename), destPath);
        } catch (java.io.IOException err) {
            Log.e(LOG_TAG, logMsg, err);
            return false; // something bad happened
        }
        Log.v(LOG_TAG, logMsg + "... done");
        return true; // all good
    }

    static boolean copyResources(@NonNull Context context) {
        if (!copySingleResource(context, ASN_DB)) {
            return false; // cannot copy
        }
        if (!copySingleResource(context, CA_BUNDLE)) {
            return false; // cannot copy
        }
        if (!copySingleResource(context, COUNTRY_DB)) {
            return false; // cannot copy
        }
        if (!copySingleResource(context, VERSION_JSON)) {
            return false; // cannot copy
        }
        return true; // all good
    }

    static byte[] readAll(@NonNull InputStream inputstream) throws java.io.IOException {
        return org.apache.commons.io.IOUtils.toByteArray(inputstream);
    }

    static boolean weNeedToCopy(@NonNull Context context) {
        try {
            byte[] bundleVersion;
            byte[] fsVersion;
            // Implementation note: we assume that the version.json file is small
            bundleVersion = readAll(context.getAssets().open(VERSION_JSON));
            fsVersion = readAll(new FileInputStream(getVersionJSONPath(context)));
            if (java.util.Arrays.equals(bundleVersion, fsVersion)) {
                return false; // Nope
            }
        } catch (java.io.IOException err) {
            Log.e(LOG_TAG, "Cannot verify whether we need to update resources", err);
            // FALLTHROUGH
        }
        return true; // Yep
    }

    // maybeUpdateResources ensures that the resources copied in the storage accessible by C/C++
    // code are current, and otherwise makes them current. The current implementation will make
    // the resources current by copying them from the assets folder. The mechanism used to decide
    // whether we need to copy resources over relies on a small file named version.json that is
    // keeping tracks of the assets version. Whenever the copy of this file saved into the storage
    // is different from the resources in the AAR, we copy resources over. Every new release of
    // this AAR will possibly cause a bump in the resources version. This function returns true
    // if all is good and false in case of error. See the logcat in such case.
    public static boolean maybeUpdateResources(@NonNull Context context) {
        if (!weNeedToCopy(context)) {
            return true; // No error occurred
        }
        return copyResources(context);
    }

    @NonNull static String getPath(@NonNull Context context, @NonNull String string) {
        return context.getFilesDir() + "/" + string;
    }

    @NonNull static String getVersionJSONPath(@NonNull Context context) {
        return getPath(context, VERSION_JSON);
    }

    // getCABundlePath returns the path where the CA bundle to be used by the nettests
    // has been copied as part of maybeCopyResources. This will be a path below
    // context.getFileDir(). Because of the transient nature of such directory, you
    // SHOULD NOT cache the value returned by this function.
    @NonNull public static String getCABundlePath(@NonNull Context context) {
        return getPath(context, CA_BUNDLE);
    }

    // getCountryDBPath is like getCABundlePath but for the MMDB country DB path.
    @NonNull public static String getCountryDBPath(@NonNull Context context) {
        return getPath(context, COUNTRY_DB);
    }

    // getASNDBPath is like getCABundlePath but for the MMDB country DB path.
    @NonNull public static String getASNDBPath(@NonNull Context context) {
        return getPath(context, ASN_DB);
    }
}
