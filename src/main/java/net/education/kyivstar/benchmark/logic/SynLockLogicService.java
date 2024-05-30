package net.education.kyivstar.benchmark.logic;


import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Logic;

public class SynLockLogicService implements Logic {
    private final IntegrationService integrationService;
    private final Object lock = new Object();

    public SynLockLogicService(IntegrationService businessLogic) {
        this.integrationService = businessLogic;
    }

    public void execute() {
        synchronized (lock) {
            integrationService.startIntegration();
        }
    }
}
