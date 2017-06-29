// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

import org.openobservatory.measurement_kit.swig.Error;
import org.openobservatory.measurement_kit.swig.LogSeverity;
import org.openobservatory.measurement_kit.swig.OrchestrateAuth;
import org.openobservatory.measurement_kit.swig.OrchestrateClient;
import org.openobservatory.measurement_kit.swig.OrchestrateRegisterProbeCallback;
import org.openobservatory.measurement_kit.swig.OrchestrateUpdateCallback;

public class OrchestrateExample {
    public static void main(String[] args) {
        System.loadLibrary("measurement_kit_java");
        final String auth_secret_file = "orchestrate_secret.json";

        List<String> supported_tests = new ArrayList<>();
        supported_tests.add("web_connectivity");
        supported_tests.add("http_invalid_request_line");

        OrchestrateClient client = new OrchestrateClient();
        client.set_supported_tests(supported_tests);
        client.set_registry_url(
            "https://registry.proteus.test.ooni.io");
        client.set_verbosity(LogSeverity.LOG_DEBUG);
        client.set_geoip_country_path("GeoIP.dat");
        client.set_geoip_asn_path("GeoIPASNum.dat");

        OrchestrateAuth auth = new OrchestrateAuth();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        // Assumption: if we cannot load authentication, then we are not
        // registered with the orchestrator. Otherwise, we are.
        if (auth.load(auth_secret_file).as_bool()) {
            client.register_probe(
                OrchestrateAuth.make_password(),
                new OrchestrateRegisterProbeCallback() {
                    @Override
                    public void callback(
                          final Error error,
                          final OrchestrateAuth auth) {
                        if (error.as_bool()) {
                            System.err.println(error.reason());
                        } else {
                            Error err = auth.dump(auth_secret_file);
                            if (err.as_bool()) {
                                System.err.println(err.reason());
                            }
                        }
                        lock.lock();
                        condition.signalAll();
                        lock.unlock();
                    }
                });

        } else {
            client.update(
                auth,
                new OrchestrateUpdateCallback() {
                    @Override
                    public void callback(
                          final Error error,
                          final OrchestrateAuth auth) {
                        if (error.as_bool()) {
                            System.err.println(error.reason());
                        } else {
                            Error err = auth.dump(auth_secret_file);
                            if (err.as_bool()) {
                                System.err.println(err.reason());
                            }
                        }
                        lock.lock();
                        condition.signalAll();
                        lock.unlock();
                    }
                });
        }

        lock.lock();
        condition.awaitUninterruptibly();
        lock.unlock();
    }
}
