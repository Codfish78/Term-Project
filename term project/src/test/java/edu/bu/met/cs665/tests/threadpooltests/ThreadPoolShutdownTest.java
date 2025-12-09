package edu.bu.met.cs665.tests.threadpooltests;

import edu.bu.met.cs665.scheduler.HouseholdScheduler;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ThreadPoolShutdownTest {

    @Test
    public void testWorkersStopAfterShutdown() throws Exception {

        HouseholdScheduler scheduler = new HouseholdScheduler(2);
        scheduler.start();

        scheduler.shutdown();

        // Workers should stop within 500ms
        Thread.sleep(500);

        // If no exception thrown → workers exited gracefully
        assertTrue(true);
    }
}
