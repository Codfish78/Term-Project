package edu.bu.met.cs665.task;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * A task that simulates preparing a household lunch.
 *
 * <p>This task performs a sequence of cooking steps and logs all activity
 * to {@code cook.log}. An ingredients file ({@code ingredients.json}) is used
 * to simulate stored pantry contents. The task may randomly fail to simulate a
 * burnt pan or other cooking mishap.
 *
 * <p>Behavior summary:
 * <ul>
 *     <li>Create an ingredients file if it does not exist</li>
 *     <li>10% chance to fail (burnt pan scenario)</li>
 *     <li>Perform a sequence of cooking steps with delays</li>
 *     <li>Log progress and completion</li>
 * </ul>
 */
public class MakeLunchTask implements HouseholdTask {

    /** File representing available ingredients. */
    private final Path ingredients = Paths.get("ingredients.json");

    /** Log file for recording cooking progress. */
    private final Path log = Paths.get("cook.log");

    /**
     * Executes the lunch preparation task.
     *
     * <p>If the task succeeds, a series of cooking steps are logged.
     * If the task fails, the method logs the failure and terminates early.
     */
    @Override
    public void execute() {
        try {
            append(log, "\n=== MakeLunchTask Start " + LocalDateTime.now() + " ===\n");

            // Initialize ingredients file if missing
            if (!Files.exists(ingredients)) {
                Files.write(ingredients,
                        "{ \"rice\": true, \"eggs\": true, \"veggies\": true }".getBytes(),
                        StandardOpenOption.CREATE);
            }

            // 10% chance to simulate cooking failure
            if (Math.random() < 0.10) {
                append(log,
                        "Cooking failed: pan burnt.\n=== MakeLunchTask Fail ===\n");
                return;
            }

            // Simulated step-by-step cooking process
            List<String> steps = Arrays.asList(
                    "Washing vegetables...",
                    "Cutting ingredients...",
                    "Heating the pan...",
                    "Stir-frying ingredients...",
                    "Plating and serving lunch..."
            );

            for (String step : steps) {
                append(log, step + "\n");
                Thread.sleep(200); // simulate time spent on each step
            }

            append(log, "=== MakeLunchTask Done ===\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends text to the specified log file, creating it if necessary.
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
