package edu.bu.met.cs665.task;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalTime;

public class CountingTask implements HouseholdTask {

    private final Path log = Paths.get("counting.log");

    @Override
    public void execute() {
        try {
            Files.write(log,
                    ("Executed at " + LocalTime.now() + "\n").getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {}
    }
}
