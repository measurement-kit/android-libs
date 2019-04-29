// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.android;

import android.content.Context;
import android.support.annotation.NonNull;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.util.Log;

// TODO(bassosimone): improve this class with help from @nuke and @xan
public class MKResources {
  private static final String LOG_TAG = "MKResources";
  private static final String CA_BUNDLE = "cacert.pem";
  private static final String GEOIP_COUNTRY_DB = "country.mmdb";
  private static final String GEOIP_ASN_DB = "asn.mmdb";

  public static void copyCABundle(@NonNull Context context, int id) {
    copyResource(context, id, CA_BUNDLE);
  }

  public static void copyGeoIPCountryDB(@NonNull Context context, int id) {
    copyResource(context, id, GEOIP_COUNTRY_DB);
  }

  public static void copyGeoIPASNDB(@NonNull Context context, int id) {
    copyResource(context, id, GEOIP_ASN_DB);
  }

  static void copyResource(@NonNull Context context, int id, @NonNull String filename) {
    // Note to self: I am not totally sure this code handles correctly
    // exceptions and resources cleanup under read/write errrors
    Log.v(LOG_TAG, "copy_resource...");
    try (InputStream in = context.getResources().openRawResource(id)) {
      try (FileOutputStream out = context.openFileOutput(filename, 0)) {
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

  @NonNull
  public static String getCABundlePath(@NonNull Context context) {
    return getPath(context, CA_BUNDLE);
  }

  @NonNull
  public static String getGeoIPCountryDBPath(@NonNull Context context) {
    return getPath(context, GEOIP_COUNTRY_DB);
  }

  @NonNull
  public static String getGeoIPASNDBPath(@NonNull Context context) {
    return getPath(context, GEOIP_ASN_DB);
  }

  @NonNull
  static String getPath(@NonNull Context context, @NonNull String string) {
    return context.getFilesDir() + "/" + string;
  }
}
