package net.education.kyivstar.benchmark.logic;


import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Task;

/**
 * Represents a task that ensures synchronization using a lock to handle multithreading.
 * Only one thread can access the method at a time, preventing concurrent execution.
 */
public class SynLockLogicTask implements Task {
    private final IntegrationService integrationService;
    private final Object lock = new Object();

    public SynLockLogicTask(IntegrationService businessLogic) {
        this.integrationService = businessLogic;
    }

    public void execute() {
        synchronized (lock) {
            integrationService.startIntegration();
        }
    }
}
