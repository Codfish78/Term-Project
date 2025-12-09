package edu.bu.met.cs665.tests.tasktests;

import edu.bu.met.cs665.task.WashDishesTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WashDishesTaskTest {

    @Test
    public void testDishCountBecomesZero() throws IOException {
        Path status = Paths.get("dishes_status.txt");
        Files.deleteIfExists(status);

        WashDishesTask task = new WashDishesTask();
        task.execute();

        String content = new String(Files.readAllBytes(status));
        int count = Integer.parseInt(content.replaceAll("[^0-9]", ""));

        assertEquals(0, count);
    }
}
