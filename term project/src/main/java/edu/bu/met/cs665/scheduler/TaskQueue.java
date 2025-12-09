package edu.bu.met.cs665.scheduler;

import edu.bu.met.cs665.task.HouseholdTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A thread-safe queue used by the scheduler to store submitted household tasks.
 *
 * <p>This class wraps a {@link LinkedBlockingQueue} to provide blocking semantics for
 * worker threads. Workers will wait (block) when the queue is empty and resume when
 * new tasks are submitted. This design ensures safe communication between producer
 * threads (task submitters) and consumer threads ({@link WorkerThread}).
 *
 * <p>Tasks are processed in FIFO order by default.
 */
public class TaskQueue {

    /** Internal thread-safe blocking queue for task storage. */
    private final BlockingQueue<HouseholdTask> queue = new LinkedBlockingQueue<>();

    /**
     * Adds a new task to the queue.
     *
     * <p>This method never blocks because {@link LinkedBlockingQueue#add(Object)} inserts
     * immediately and throws an exception only if capacity is restricted (unlimited here).
     *
     * @param task the task to be added
     */
    public void put(HouseholdTask task) {
        queue.add(task);
    }

    /**
     * Retrieves and removes the next available task from the queue, waiting if necessary
     * until a task becomes available.
     *
     * @return the next {@link HouseholdTask} to be executed
     * @throws InterruptedException if the waiting thread is interrupted (typically during shutdown)
     */
    public HouseholdTask take() throws InterruptedException {
        return queue.take();
    }
}
