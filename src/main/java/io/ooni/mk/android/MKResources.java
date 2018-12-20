// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.android;

import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.util.Log;

// TODO(bassosimone): improve this class with help from @nuke and @xan
public class MKResources {
  private static final String LOG_TAG = "MKResources";
  private static final String CA_BUNDLE = "cacert.pem";
  private static final String GEOIP_COUNTRY_DB = "country.mmdb";
  private static final String GEOIP_ASN_DB = "asn.mmdb";

  public static void copyCABundle(Activity activity, int id) {
    copyResource(activity, id, CA_BUNDLE);
  }

  public static void copyGeoIPCountryDB(Activity activity, int id) {
    copyResource(activity, id, GEOIP_COUNTRY_DB);
  }

  public static void copyGeoIPASNDB(Activity activity, int id) {
    copyResource(activity, id, GEOIP_ASN_DB);
  }

  static void copyResource(Activity activity, int id, String filename) {
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

  public static String getCABundlePath(Activity activity) {
    return getPath(activity, CA_BUNDLE);
  }

  public static String getGeoIPCountryDBPath(Activity activity) {
    return getPath(activity, GEOIP_COUNTRY_DB);
  }

  public static String getGeoIPASNDBPath(Activity activity) {
    return getPath(activity, GEOIP_ASN_DB);
  }

  static String getPath(Activity activity, String string) {
    return activity.getFilesDir() + "/" + string;
  }
}
