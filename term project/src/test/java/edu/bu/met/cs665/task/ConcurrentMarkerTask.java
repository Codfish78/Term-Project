package edu.bu.met.cs665.task;

import edu.bu.met.cs665.task.HouseholdTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentMarkerTask implements HouseholdTask {

    private final AtomicInteger counter;
    private long startTime;

    public ConcurrentMarkerTask(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public void execute() {
        startTime = System.currentTimeMillis();
        counter.incrementAndGet();

        try {
            Thread.sleep(300); // simulate long work
        } catch (InterruptedException ignored) { }
    }

    public long getStartTime() {
        return startTime;
    }
}
