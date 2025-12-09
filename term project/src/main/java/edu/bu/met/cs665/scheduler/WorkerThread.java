package edu.bu.met.cs665.scheduler;

import edu.bu.met.cs665.task.HouseholdTask;

/**
 * A worker thread responsible for continuously pulling tasks from the shared
 * {@link TaskQueue} and executing them.
 *
 * <p>This class represents the "consumer" side of a producer-consumer model.
 * It blocks when no tasks are available and resumes when new tasks are submitted.
 *
 * <p>The thread supports graceful shutdown: the {@link #shutdown()} method
 * interrupts the thread and signals it to stop processing tasks.
 */
public class WorkerThread extends Thread {

    /** Queue from which this worker retrieves tasks. */
    private final TaskQueue queue;

    /** Used to gracefully stop the worker. Volatile ensures visibility across threads. */
    private volatile boolean running = true;

    /**
     * Creates a new worker thread.
     *
     * @param queue the shared task queue
     * @param name  the thread name for debugging/logging
     */
    public WorkerThread(TaskQueue queue, String name) {
        super(name);
        this.queue = queue;
    }

    /**
     * Continuously fetches tasks from the queue and executes them.
     *
     * <p>The thread blocks when no tasks are available. Shutdown occurs when
     * the thread is interrupted or when {@link #running} is set to false.
     */
    @Override
    public void run() {
        while (running) {
            try {
                // Block until a new task is available.
                HouseholdTask task = queue.take();

                System.out.println(getName() + " executing task: "
                        + task.getClass().getSimpleName());

                task.execute();

            } catch (InterruptedException e) {
                // Expected during shutdown. Break out of the loop.
                running = false;
                break;
            }
        }

        System.out.println(getName() + " has stopped.");
    }

    /**
     * Signals the worker to stop after its current blocking operation and interrupts it.
     *
     * <p>This method is thread-safe because {@link #running} is volatile.
     * The interrupt forces the blocking {@code queue.take()} call to return early.
     */
    public void shutdown() {
        running = false;
        this.interrupt();
    }
}
