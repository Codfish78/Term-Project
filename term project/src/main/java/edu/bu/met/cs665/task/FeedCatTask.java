package edu.bu.met.cs665.task;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

/**
 * A task that simulates feeding a household cat.
 *
 * <p>This task reads and updates a simple JSON-like text file
 * ( {@code cat_status.json} ) representing the cat's hunger level.
 * Feeding reduces the hunger level. However, the cat may randomly refuse
 * to eat, simulating realistic behavior.
 *
 * <p>All activity is logged to {@code feedcat.log}.
 */
public class FeedCatTask implements HouseholdTask {

    /** File representing the cat's current hunger state. */
    private final Path jsonFile = Paths.get("cat_status.json");

    /** Log file recording each feeding attempt. */
    private final Path logFile = Paths.get("feedcat.log");

    /**
     * Executes the cat-feeding task.
     *
     * <p>Behavior:
     * <ul>
     *   <li>Reads the cat's hunger level from a JSON-like file</li>
     *   <li>15% chance that the cat refuses to eat</li>
     *   <li>Otherwise reduces hunger level by 50</li>
     *   <li>Writes updated state back to the file</li>
     * </ul>
     */
    @Override
    public void execute() {
        try {
            append(logFile, "\n=== FeedCatTask Start " + LocalDateTime.now() + " ===\n");

            // Initialize cat status file if missing
            if (!Files.exists(jsonFile)) {
                Files.write(jsonFile,
                        "{ \"name\": \"Momo\", \"hungerLevel\": 70 }".getBytes(),
                        StandardOpenOption.CREATE);
            }

            String json = new String(Files.readAllBytes(jsonFile));
            int hunger = extractHunger(json);

            append(logFile, "Initial hunger level: " + hunger + "\n");

            // 15% chance the cat refuses to eat
            if (Math.random() < 0.15) {
                append(logFile,
                        "Cat refuses to eat. Feeding aborted.\n=== FeedCatTask Fail ===\n");
                return;
            }

            // Simulate time taken to prepare food
            Thread.sleep(300);

            // Feed the cat: hunger -50, not below 0
            hunger = Math.max(0, hunger - 50);
            String newJson = "{ \"name\": \"Momo\", \"hungerLevel\": " + hunger + " }";

            // Overwrite cat status file
            Files.write(jsonFile, newJson.getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

            append(logFile,
                    "New hunger level: " + hunger + "\n=== FeedCatTask Done ===\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts the hunger level (integer) from a JSON-like string.
     *
     * @param json the text content of the cat status file
     * @return an integer hunger level, or 70 if parsing fails
     */
    private int extractHunger(String json) {
        try {
            String num = json.replaceAll("[^0-9]", "");
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 70; // fallback default
        }
    }

    /**
     * Appends text to a log file, creating it if necessary.
     *
     * @param path the log file
     * @param text the text to append
     * @throws IOException if writing to the file fails
     */
    private void append(Path path, String text) throws IOException {
        Files.write(path, text.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
