package net.education.kyivstar.benchmark.logic;


import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Task;

/**
 * Represents a task that utilizes synchronized modifier logic to ensure thread safe execution
 * allowing only one thread to access the method.
 */
public class SynLogicTask implements Task {
    private final IntegrationService integrationService;

    public SynLogicTask(IntegrationService businessLogic) {
        this.integrationService = businessLogic;
    }

    public synchronized void execute() {
        integrationService.startIntegration();
    }
}
