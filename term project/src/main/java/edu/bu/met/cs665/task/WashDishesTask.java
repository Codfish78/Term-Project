package edu.bu.met.cs665.task;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * A task that simulates washing household dishes.
 *
 * <p>This task maintains a simple status file ({@code dishes_status.txt})
 * containing an integer representing the number of dirty dishes. When executed,
 * the task reduces this count to zero and logs all activity to
 * {@code washdishes.log}.
 *
 * <p>Behavior summary:
 * <ul>
 *     <li>Initialize dish count randomly (3–10) if the status file does not exist</li>
 *     <li>Load the current number of dirty dishes</li>
 *     <li>Simulate washing time</li>
 *     <li>Set dish count to zero</li>
 *     <li>Log all relevant state changes</li>
 * </ul>
 */
public class WashDishesTask implements HouseholdTask {

    /** Status file storing the number of dirty dishes. */
    private final Path status = Paths.get("dishes_status.txt");

    /** Log file for recording washing activity. */
    private final Path log = Paths.get("washdishes.log");

    /**
     * Executes the dish-washing task.
     *
     * <p>This method logs:
     * <ul>
     *     <li>Start time</li>
     *     <li>Initial number of dirty dishes</li>
     *     <li>Completion message once dishes are cleaned</li>
     * </ul>
     *
     * <p>All exceptions are caught internally to ensure worker threads in the
     * household scheduler remain stable.
     */
    @Override
    public void execute() {
        try {
            append(log, "\n=== WashDishesTask Start " + LocalDateTime.now() + " ===\n");

            // Initialize dish count between 3 and 10 if status file does not exist
            if (!Files.exists(status)) {
                int count = new Random().nextInt(8) + 3;
                Files.write(status, ("count=" + count).getBytes(),
                        StandardOpenOption.CREATE);
            }

            // Load current dish count
            String content = new String(Files.readAllBytes(status));
            int count = Integer.parseInt(content.replaceAll("[^0-9]", ""));

            append(log, "Initial dish count: " + count + "\n");

            // Simulate washing time
            Thread.sleep(300);

            // Mark dishes as clean
            Files.write(status, "count=0".getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

            append(log, "All dishes cleaned successfully.\n=== WashDishesTask Done ===\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends text to a log file, creating it if necessary.
     *
     * @param path the log file
     * @param text the text to append
     * @throws IOException if the write operation fails
     */
    private void append(Path path, String text) throws IOException {
        Files.write(path, text.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
