package edu.bu.met.cs665.scheduler;

import edu.bu.met.cs665.task.HouseholdTask;
import java.util.ArrayList;
import java.util.List;

/**
 * A lightweight task scheduler that manages a pool of worker threads.
 *
 * <p>This scheduler receives {@link HouseholdTask} instances and places them into a
 * shared {@link TaskQueue}. A fixed number of {@link WorkerThread}s continuously
 * take tasks from the queue and execute them. This design simulates a simple
 * thread pool for household task automation.
 *
 * <p>The scheduler supports:
 * <ul>
 *     <li>Submitting tasks</li>
 *     <li>Starting worker threads</li>
 *     <li>Graceful shutdown</li>
 * </ul>
 *
 * <p>This class is intentionally simple because it is used as part of a CS665
 * course project demonstrating the Command and Thread Pool design patterns.
 */
public class HouseholdScheduler {

    /** Shared queue used by all workers to fetch tasks. */
    private final TaskQueue queue = new TaskQueue();

    /** A fixed-size list of worker threads. */
    private final List<WorkerThread> workers = new ArrayList<>();

    /**
     * Creates a scheduler with a specified number of worker threads.
     *
     * @param workerCount the number of worker threads to spawn
     */
    public HouseholdScheduler(int workerCount) {
        for (int i = 0; i < workerCount; i++) {
            workers.add(new WorkerThread(queue, "Worker-" + (i + 1)));
        }
    }

    /**
     * Starts all worker threads. Workers begin pulling tasks from the queue
     * and executing them as soon as tasks are submitted.
     */
    public void start() {
        for (WorkerThread w : workers) {
            w.start();
        }
    }

    /**
     * Submits a new {@link HouseholdTask} to the scheduler.
     *
     * @param task the task to be executed by the worker threads
     */
    public void submit(HouseholdTask task) {
        queue.put(task);
    }

    /**
     * Gracefully shuts down all worker threads.
     *
     * <p>Each worker is interrupted and instructed to stop after completing
     * its current task. No new tasks will be executed after shutdown.
     */
    public void shutdown() {
        System.out.println("Scheduler is shutting down...");
        for (WorkerThread w : workers) {
            w.shutdown();
        }
    }
}
