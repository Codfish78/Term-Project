package edu.bu.met.cs665.tests.tasktests;

import edu.bu.met.cs665.task.CleanRoomTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CleanRoomTaskTest {

    @Test
    public void testCleanRoomProducesLogFile() throws IOException {
        Path logFile = Paths.get("cleanroom.log");
        Files.deleteIfExists(logFile);

        CleanRoomTask task = new CleanRoomTask();
        task.execute();

        assertTrue(Files.exists(logFile));
        assertTrue(Files.size(logFile) > 0);
    }
}
