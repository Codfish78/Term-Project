package edu.bu.met.cs665.tests.threadpooltests;

import edu.bu.met.cs665.scheduler.HouseholdScheduler;
import edu.bu.met.cs665.task.OrderedTask;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThreadPoolFifoTest {

    @Test
    public void testFIFOOrder() throws Exception {

        StringBuilder log = new StringBuilder();

        HouseholdScheduler scheduler = new HouseholdScheduler(1); // force single thread
        scheduler.start();

        scheduler.submit(new OrderedTask(log, "A"));
        scheduler.submit(new OrderedTask(log, "B"));
        scheduler.submit(new OrderedTask(log, "C"));

        Thread.sleep(500);
        scheduler.shutdown();

        assertEquals("ABC", log.toString());
    }
}
