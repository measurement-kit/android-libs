// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import java.io.File;
import java.io.FileInputStream;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

// MKResourcesManager allows to keep resources required by C/C++ code up to date. The policy used
// to update resources is described in the documentation of maybeUpdateResources.
public class MKResourcesManager {
    static private final String ASN_DB = "asn.mmdb";             // The MMDB ASN database name
    static private final String CA_BUNDLE = "ca-bundle.pem";     // The CA bundle name
    static private final String COUNTRY_DB = "country.mmdb";     // The country database name
    static private final String LOG_TAG = "MKResourcesManager";  // Tag used for logging
    static private final String VERSION_JSON = "version.json";   // The version manifest file name

    // RESOURCES contains a list of resources managed by this class
    static private final String[] RESOURCES = new String[]{
            ASN_DB, CA_BUNDLE, COUNTRY_DB, VERSION_JSON,
    };

    // copyResources copies all the resources from the assets to a location where C/C++ code
    // should be able to access them. Returns whether we succeeded in copying.
    static private boolean copyResources(@NonNull Context context) {
        for (String resource : RESOURCES) {
            try {
                FileUtils.copyToFile(context.getAssets().open(resource),
                        getResourceAsFile(context, resource));
            } catch (java.io.IOException err) {
                Log.e(LOG_TAG, "cannot copy resource", err);
                return false;
            }
        }
        return true;
    }

    // equals returns true if the content of file equals the content of resource and returns
    // false otherwise (including in the definition of otherwise any error case).
    static private boolean
    equals(@NonNull File file, @NonNull Context context, @NonNull String resource) {
        try {
            return IOUtils.contentEquals(new FileInputStream(file),
                    context.getAssets().open(resource));
        } catch (java.io.IOException err) {
            Log.e(LOG_TAG, "cannot compare file and resource", err);
            return false;
        }
    }

    // maybeUpdateResources ensures that the resources copied in the storage accessible by C/C++
    // code are current, and otherwise makes them current. The current implementation will make
    // the resources current by copying them from the assets folder. The mechanism used to decide
    // whether we need to copy resources over relies on a small file named version.json that is
    // keeping track of the assets version. Whenever the copy of this file saved into the storage
    // is different from the resources in the AAR, we copy resources over. We also perform the
    // copy if any required file is missing. Every new release of this AAR will possibly cause a
    // bump in the resources version. This function returns true if all is good and false in
    // case of error. See the logcat in the latter case to understand what was wrong.
    public static boolean maybeUpdateResources(@NonNull Context context) {
        for (String resource : RESOURCES) {
            File file = getResourceAsFile(context, resource);
            if (!file.exists() ||
                    (resource.compareTo(VERSION_JSON) == 0 && !equals(file, context, resource))) {
                return copyResources(context);
            }
        }
        return true; // Doing nothing did not cause any error
    }

    // maybeCleanResources will clean the resources if they are installed. This method
    // is mainly useful when running integration tests.
    public static void maybeCleanResources(@NonNull Context context) {
        for (String resource : RESOURCES) {
            getResourceAsFile(context, resource).delete();
        }
    }

    // getResourceAsFile returns the resource identified by string as a File.
    @NonNull private static File
    getResourceAsFile(@NonNull Context context, @NonNull String string) {
        return new File(context.getCacheDir(), string);
    }

    // getCABundlePath returns the path where the CA bundle to be used by the nettests
    // has been copied as part of maybeCopyResources. This will be a path below
    // context.getFileDir(). Because of the transient nature of such directory, you
    // SHOULD NOT cache the value returned by this function.
    @NonNull public static String getCABundlePath(@NonNull Context context) {
        return getResourceAsFile(context, CA_BUNDLE).toString();
    }

    // getCountryDBPath is like getCABundlePath but for the MMDB country DB path.
    @NonNull public static String getCountryDBPath(@NonNull Context context) {
        return getResourceAsFile(context, COUNTRY_DB).toString();
    }

    // getASNDBPath is like getCABundlePath but for the MMDB country DB path.
    @NonNull public static String getASNDBPath(@NonNull Context context) {
        return getResourceAsFile(context, ASN_DB).toString();
    }
}
