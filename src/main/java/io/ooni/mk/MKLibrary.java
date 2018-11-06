// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

// "silence in the library!" (cit.)
public class MKLibrary {
  // You can call this function more than once without harm. For more
  // on this topic, see <http://stackoverflow.com/a/21879539>.
  public static void load() {
      System.loadLibrary("measurement_kit");
  }
}
