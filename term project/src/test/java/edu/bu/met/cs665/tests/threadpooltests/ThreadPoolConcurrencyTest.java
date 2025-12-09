package edu.bu.met.cs665.tests.threadpooltests;

import edu.bu.met.cs665.scheduler.HouseholdScheduler;
import edu.bu.met.cs665.task.ConcurrentMarkerTask;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

public class ThreadPoolConcurrencyTest {

    @Test
    public void testTasksRunConcurrently() throws Exception {

        AtomicInteger counter = new AtomicInteger(0);

        ConcurrentMarkerTask task1 = new ConcurrentMarkerTask(counter);
        ConcurrentMarkerTask task2 = new ConcurrentMarkerTask(counter);

        HouseholdScheduler scheduler = new HouseholdScheduler(2);
        scheduler.start();

        scheduler.submit(task1);
        scheduler.submit(task2);

        Thread.sleep(200); // wait until both start

        scheduler.shutdown();

        // Both tasks should have started within 100 ms of each other
        long diff = Math.abs(task1.getStartTime() - task2.getStartTime());

        assertTrue(
                "Tasks should run concurrently; start time difference = " + diff,
                diff < 100
        );
    }
}
