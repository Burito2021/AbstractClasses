package net.education.kyivstar.benchmark.logic;


import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Logic;

public class SynLogicService implements Logic {
    private final IntegrationService integrationService;

    public SynLogicService(IntegrationService businessLogic) {
        this.integrationService = businessLogic;
    }

    public synchronized void execute() {
        integrationService.startIntegration();
    }
}
