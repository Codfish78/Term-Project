package edu.bu.met.cs665.task;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

/**
 * A task that simulates washing laundry by updating a simple status file.
 *
 * <p>This task writes its progress and results to {@code laundry.log} and
 * maintains the laundry state in {@code laundry_status.txt}. The task may
 * randomly fail to simulate an overloaded washing machine.
 *
 * <p>Behavior summary:
 * <ul>
 *     <li>Initialize laundry status if the file does not exist</li>
 *     <li>20% chance to simulate machine overload and abort the task</li>
 *     <li>Otherwise update the laundry status from "dirty" to "washed"</li>
 *     <li>Log all relevant actions and states</li>
 * </ul>
 */
public class LaundryTask implements HouseholdTask {

    /** File that stores the laundry status (dirty/washed). */
    private final Path statusFile = Paths.get("laundry_status.txt");

    /** Log file for recording execution details. */
    private final Path logFile = Paths.get("laundry.log");

    /**
     * Executes the laundry-washing task.
     *
     * <p>This method logs:
     * <ul>
     *     <li>Start time</li>
     *     <li>Whether initialization occurred</li>
     *     <li>Whether the task failed due to overload</li>
     *     <li>Success and updated laundry state</li>
     * </ul>
     *
     * <p>All exceptions are caught internally to avoid interrupting worker threads.
     */
    @Override
    public void execute() {
        try {
            append(logFile,
                    "\n=== LaundryTask Start " + LocalDateTime.now() + " ===\n");

            // Create default laundry status file if it does not exist
            if (!Files.exists(statusFile)) {
                Files.write(statusFile,
                        "dirty\ntshirt\nsocks\njeans\n".getBytes(),
                        StandardOpenOption.CREATE);
            }

            // 20% chance of failure
            if (Math.random() < 0.20) {
                append(logFile,
                        "Washing machine overloaded. Aborting task.\n=== LaundryTask Fail ===\n");
                return;
            }

            // Simulate washing time
            Thread.sleep(400);

            // Update laundry status to washed
            Files.write(statusFile,
                    "washed\ntshirt\nsocks\njeans\n".getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);

            append(logFile,
                    "Laundry completed successfully. Status updated to 'washed'.\n" +
                            "=== LaundryTask Done ===\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends text to the specified log file.
     *
     * @param path the log file
     * @param text the text to append
     * @throws IOException if writing fails
     */
    private void append(Path path, String text) throws IOException {
        Files.write(path, text.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
