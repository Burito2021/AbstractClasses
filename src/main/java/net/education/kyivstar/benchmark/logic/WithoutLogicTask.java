package net.education.kyivstar.benchmark.logic;

import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Task;



public class WithoutLogicTask implements Task {
    private final IntegrationService integrationService;

    public WithoutLogicTask(IntegrationService businessLogic) {
        this.integrationService = businessLogic;
    }

    public void execute() {
        integrationService.startIntegration();
    }
}
