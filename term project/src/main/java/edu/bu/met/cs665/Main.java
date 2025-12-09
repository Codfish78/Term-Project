package edu.bu.met.cs665;

import edu.bu.met.cs665.scheduler.HouseholdScheduler;
import edu.bu.met.cs665.task.*;

/**
 * Entry point for demonstrating the household task scheduling system.
 *
 * <p>This example creates a scheduler with two worker threads and submits several
 * concrete {@link HouseholdTask} commands for execution. The example pauses briefly
 * to allow tasks to run before shutting down the scheduler.
 */
public class Main {

    /**
     * Runs the demo program.
     *
     * @param args command-line arguments (not used)
     * @throws InterruptedException if the main thread is interrupted during sleep
     */
    public static void main(String[] args) throws InterruptedException {

        HouseholdScheduler scheduler = new HouseholdScheduler(2);
        scheduler.start();

        // Submit several example tasks
        scheduler.submit(new CleanRoomTask());
        scheduler.submit(new FeedCatTask());
        scheduler.submit(new LaundryTask());
        scheduler.submit(new WaterPlantTask());
        scheduler.submit(new MakeLunchTask());
        scheduler.submit(new WashDishesTask());

        // Allow time for workers to process tasks
        Thread.sleep(3000);

        scheduler.shutdown();
    }
}
