package edu.bu.met.cs665.task;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

/**
 * A task that simulates watering a household plant.
 *
 * <p>The task reads the plant's dryness level from a status file
 * ({@code plant_status.txt}), waters the plant if necessary, and logs all actions to
 * {@code waterplant.log}. If the dryness level exceeds a defined threshold, the task
 * fails to simulate a scenario where the plant is already too dry and at risk of wilting.
 *
 * <p>Behavior summary:
 * <ul>
 *     <li>Create a default dryness file if none exists</li>
 *     <li>Read the current dryness level</li>
 *     <li>Fail if dryness exceeds 80</li>
 *     <li>Otherwise water the plant, resetting dryness to 0</li>
 *     <li>Log all state changes and outcomes</li>
 * </ul>
 */
public class WaterPlantTask implements HouseholdTask {

    /** File storing the plant's dryness level. */
    private final Path status = Paths.get("plant_status.txt");

    /** Log file used to record watering activity. */
    private final Path log = Paths.get("waterplant.log");

    /**
     * Executes the plant-watering task.
     *
     * <p>This method logs:
     * <ul>
     *     <li>Start time</li>
     *     <li>Current dryness level</li>
     *     <li>Failure message if plant is too dry</li>
     *     <li>Success message and updated dryness level</li>
     * </ul>
     *
     * <p>All errors are caught internally to prevent worker thread malfunction.
     */
    @Override
    public void execute() {
        try {
            append(log, "\n=== WaterPlantTask Start " + LocalDateTime.now() + " ===\n");

            // Initialize dryness level if file is missing
            if (!Files.exists(status)) {
                Files.write(status, "dryLevel=75".getBytes(), StandardOpenOption.CREATE);
            }

            // Read the current dryness level
            String content = new String(Files.readAllBytes(status));
            int dry = Integer.parseInt(content.replaceAll("[^0-9]", ""));

            append(log, "Current dryness level: " + dry + "\n");

            // Simulate failure: too dry to recover
            if (dry > 80) {
                append(log,
                        "Plant is too dry and may wilt. Watering aborted.\n=== WaterPlantTask Fail ===\n");
                return;
            }

            // Simulate watering duration
            Thread.sleep(300);

            // Update dryness level to zero
            Files.write(status, "dryLevel=0".getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

            append(log, "Watering complete. Dryness level reset to 0.\n=== WaterPlantTask Done ===\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends text to a log file, creating it if necessary.
     *
     * @param path the file to write to
     * @param text the text to append
     * @throws IOException if writing fails
     */
    private void append(Path path, String text) throws IOException {
        Files.write(path, text.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
