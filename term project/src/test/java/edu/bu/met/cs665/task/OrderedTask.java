package edu.bu.met.cs665.task;

public class OrderedTask implements HouseholdTask {

    private final StringBuilder log;
    private final String marker;

    public OrderedTask(StringBuilder log, String marker) {
        this.log = log;
        this.marker = marker;
    }

    @Override
    public void execute() {
        log.append(marker);
    }
}
