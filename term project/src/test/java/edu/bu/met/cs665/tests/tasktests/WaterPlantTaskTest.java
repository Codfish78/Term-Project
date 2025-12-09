package edu.bu.met.cs665.tests.tasktests;

import edu.bu.met.cs665.task.WaterPlantTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WaterPlantTaskTest {

    @Test
    public void testPlantDrynessReset() throws IOException {
        Path status = Paths.get("plant_status.txt");
        Files.deleteIfExists(status);

        WaterPlantTask task = new WaterPlantTask();
        task.execute();

        String content = new String(Files.readAllBytes(status));
        int dry = Integer.parseInt(content.replaceAll("[^0-9]", ""));
        assertTrue(dry == 0);
    }
}
