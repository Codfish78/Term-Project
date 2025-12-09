package edu.bu.met.cs665.tests.tasktests;

import edu.bu.met.cs665.task.MakeLunchTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MakeLunchTaskTest {

    @Test
    public void testMakeLunchProducesLog() throws IOException {
        Path log = Paths.get("cook.log");
        Files.deleteIfExists(log);

        MakeLunchTask task = new MakeLunchTask();

        boolean success = false;
        for (int i = 0; i < 5; i++) {
            task.execute();
            if (Files.exists(log) && Files.size(log) > 0) {
                success = true;
                break;
            }
        }

        assertTrue(success);
    }
}
