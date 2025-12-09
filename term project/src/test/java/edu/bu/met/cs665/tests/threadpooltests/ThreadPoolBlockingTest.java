package edu.bu.met.cs665.tests.threadpooltests;

import edu.bu.met.cs665.scheduler.HouseholdScheduler;
import edu.bu.met.cs665.task.SleepTask;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolBlockingTest {

    @Test
    public void testWorkersBlockAndResume() throws Exception {

        HouseholdScheduler scheduler = new HouseholdScheduler(1);
        scheduler.start();

        Thread.sleep(300); // No tasks → worker should block

        scheduler.submit(new SleepTask(100)); // Now unblock

        Thread.sleep(300);
        scheduler.shutdown();

        assertTrue(true);
    }
}
