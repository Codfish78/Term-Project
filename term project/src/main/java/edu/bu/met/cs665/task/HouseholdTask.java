package edu.bu.met.cs665.task;

/**
 * Represents a generic unit of work that can be executed by the household
 * task scheduler.
 *
 * <p>This interface follows the Command design pattern, where each task is
 * encapsulated as an object providing a single {@link #execute()} method.
 * Concrete implementations may perform a wide range of actions such as
 * cleaning, feeding pets, cooking, or updating status files.
 *
 * <p>All task classes are expected to be self-contained and should handle
 * their own exceptions internally, ensuring that worker threads in the
 * scheduler remain resilient and continue processing additional tasks.
 */
public interface HouseholdTask {

    /**
     * Executes the task's behavior.
     *
     * <p>Implementations should avoid throwing checked exceptions out of this
     * method. Any I/O or interruption exceptions should be handled internally so
     * that the scheduling system remains robust.
     */
    void execute();
}
