package net.education.kyivstar.benchmark.logic;

import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Logic;

public class WithoutLogicService implements Logic {
    private final IntegrationService integrationService;

    public WithoutLogicService(IntegrationService businessLogic) {
        this.integrationService = businessLogic;
    }

    public void execute() {
        integrationService.startIntegration();
    }
}
