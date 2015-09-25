package edu.nus.h2p.suite;

import edu.nus.h2p.service.DataLoadingServiceTest;
import edu.nus.h2p.util.DtwTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DtwTest.class, DataLoadingServiceTest.class} )
public final class TestSuite {
}
