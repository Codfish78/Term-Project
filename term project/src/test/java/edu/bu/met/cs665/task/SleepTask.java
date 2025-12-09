package edu.bu.met.cs665.task;

public class SleepTask implements HouseholdTask {

    private final long sleepMs;

    public SleepTask(long sleepMs) {
        this.sleepMs = sleepMs;
    }

    @Override
    public void execute() {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException ignored) { }
    }
}
