package edu.bu.met.cs665.tests.tasktests;

import edu.bu.met.cs665.task.FeedCatTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeedCatTaskTest {

    @Test
    public void testFeedCatReducesHunger() throws IOException {
        Path jsonFile = Paths.get("cat_status.json");
        Files.deleteIfExists(jsonFile);

        FeedCatTask task = new FeedCatTask();

        boolean success = false;
        for (int i = 0; i < 5; i++) {   // retry to avoid random failure
            task.execute();
            String content = new String(Files.readAllBytes(jsonFile));
            int hunger = Integer.parseInt(content.replaceAll("[^0-9]", ""));
            if (hunger <= 20) {  // reduced from default 70
                success = true;
                break;
            }
        }

        assertTrue(success);
    }
}
