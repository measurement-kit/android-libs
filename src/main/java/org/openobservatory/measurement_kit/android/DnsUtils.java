// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.android;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DnsUtils {

    public static final String get_device_dns() {
        String hostname = "8.8.8.8";
        ArrayList<String> nameservers = get_dns_internal();
        if (!nameservers.isEmpty()) {
            for (String s : nameservers) {
                hostname = s;
                break;
            }
        }
        return hostname;
    }

    private static ArrayList<String> get_dns_internal() {
        /*
            Implementation note: Using 4 branches to show which errors may occur, otherwise
            we can just catch Exception. Also, note that this method may not work on all the
            possible devices and Android versions (I recall reading that on Stack Overflow
            more than one year ago but never saved the link and cannot find it now).
         */
        ArrayList<String> servers = new ArrayList<String>();
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
            Method method = SystemProperties.getMethod("get", String.class);
            for (String name : new String[]{"net.dns1", "net.dns2", "net.dns3", "net.dns4",}) {
                String value = (String) method.invoke(null, name);
                if (value != null && !value.equals("") && !servers.contains(value)) {
                    servers.add(value);
                }
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "getDNS: error: " + e);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "getDNS: error: " + e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "getDNS: error: " + e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getDNS: error: " + e);
        } catch (Exception e) {
            Log.e(TAG, "getDNS: error: " + e); /* Just in case ... */
        }
        return servers;
    }

    private static final String TAG = "DnsUtils";
}
