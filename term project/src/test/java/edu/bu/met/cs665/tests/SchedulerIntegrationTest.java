package edu.bu.met.cs665.tests;

import edu.bu.met.cs665.scheduler.HouseholdScheduler;
import edu.bu.met.cs665.task.CleanRoomTask;
import org.junit.Test;

import java.nio.file.*;

import static org.junit.Assert.assertTrue;

public class SchedulerIntegrationTest {

    @Test
    public void testSchedulerExecutesTasks() throws Exception {
        Path log = Paths.get("cleanroom.log");
        Files.deleteIfExists(log);

        HouseholdScheduler scheduler = new HouseholdScheduler(2);
        scheduler.start();

        scheduler.submit(new CleanRoomTask());

        Thread.sleep(500);
        scheduler.shutdown();

        assertTrue(Files.exists(log));
    }
}
