package io.ooni.mk.androidTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MKGeoIPLookupTest.class,
        MKNDTTest.class,
        MKOrchestraTest.class,
        MKVersionTest.class,
        MKWebConnectivityTest.class
})

public class UnitTestSuite {
}
