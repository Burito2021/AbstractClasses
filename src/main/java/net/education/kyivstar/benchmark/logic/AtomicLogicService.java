package net.education.kyivstar.benchmark.logic;


import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Logic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicLogicService implements Logic {
    private final IntegrationService integrationService;
    private final AtomicBoolean lock = new AtomicBoolean(false);

    public AtomicLogicService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @Override
    public void execute() {
        if (!lock.compareAndSet(false, true)) {
            return;
        }
        try {
            integrationService.startIntegration();
        } finally {
            lock.set(false);
        }
    }
}
