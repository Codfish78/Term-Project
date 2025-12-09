package edu.bu.met.cs665.task;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A task that simulates cleaning a room by reducing dust levels and organizing items.
 *
 * <p>This task writes detailed log output to a file named {@code cleanroom.log}.
 * The cleaning process includes:
 * <ul>
 *     <li>Generating an initial dust level</li>
 *     <li>Simulating cleaning time</li>
 *     <li>Resetting the dust level to zero</li>
 *     <li>Sorting common household items to simulate tidying</li>
 * </ul>
 *
 * <p>This class is used to demonstrate the Command design pattern within the
 * household automation scheduler.
 */
public class CleanRoomTask implements HouseholdTask {

    /** Log file that records task activity. */
    private final Path logFile = Paths.get("cleanroom.log");

    /**
     * Executes the room cleaning task.
     *
     * <p>The method logs:
     * <ul>
     *     <li>Start timestamp</li>
     *     <li>Initial dust level</li>
     *     <li>Completion details including new dust level and sorted items list</li>
     * </ul>
     *
     * <p>Any I/O or interruption errors are printed to the console but do not halt execution.
     */
    @Override
    public void execute() {
        try {
            append(logFile,
                    "\n=== CleanRoomTask Start " + LocalDateTime.now() + " ===\n");

            // Simulate items scattered in the room
            List<String> items = new ArrayList<>(
                    Arrays.asList("socks", "books", "bottles", "paper"));

            // Random dust level between 30–100
            int dustLevel = new Random().nextInt(70) + 30;

            append(logFile, "Initial dust level: " + dustLevel + "\n");

            // Simulate cleaning time
            Thread.sleep(300);

            // Cleaning complete
            dustLevel = 0;
            Collections.sort(items);  // simulate tidying items alphabetically

            append(logFile, "Dust cleaned. New dust level: " + dustLevel + "\n");
            append(logFile, "Items organized: " + items + "\n");

            append(logFile,
                    "=== CleanRoomTask Done ===\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends text to a file, creating it if it does not exist.
     *
     * @param path the file to append to
     * @param text the content to append
     * @throws IOException if the file cannot be written
     */
    private void append(Path path, String text) throws IOException {
        Files.write(path, text.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
