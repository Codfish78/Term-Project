package edu.bu.met.cs665.tests.tasktests;

import edu.bu.met.cs665.task.LaundryTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaundryTaskTest {

    @Test
    public void testLaundryStatusUpdated() throws IOException {
        Path status = Paths.get("laundry_status.txt");
        Files.deleteIfExists(status);

        LaundryTask task = new LaundryTask();

        boolean success = false;
        for (int i = 0; i < 5; i++) {
            task.execute();
            if (Files.exists(status)) {
                String s = new String(Files.readAllBytes(status));
                if (s.contains("washed")) {
                    success = true;
                    break;
                }
            }
        }

        assertTrue(success);
    }
}
