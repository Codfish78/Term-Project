package edu.bu.met.cs665.tests.threadpooltests;

import edu.bu.met.cs665.scheduler.HouseholdScheduler;
import edu.bu.met.cs665.task.CountingTask;
import org.junit.Test;
import java.nio.file.*;
import java.util.List;

import static org.junit.Assert.*;

public class ThreadPoolCompletenessTest {

    @Test
    public void testNoTaskIsLost() throws Exception {

        Path log = Paths.get("counting.log");
        Files.deleteIfExists(log);

        HouseholdScheduler scheduler = new HouseholdScheduler(3);
        scheduler.start();

        for (int i = 0; i < 20; i++) {
            scheduler.submit(new CountingTask());
        }

        Thread.sleep(1500);
        scheduler.shutdown();

        List<String> lines = Files.readAllLines(log);

        assertEquals("All tasks should be executed", 20, lines.size());
    }
}
