package net.education.kyivstar.benchmark.logic;


import net.education.kyivstar.benchmark.IntegrationService;
import net.education.kyivstar.benchmark.Task;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a task that performs a logic operation atomically,
 * allowing only one thread at a time to execute the method.
 */
public class AtomicLogicTask implements Task {
    private final IntegrationService integrationService;
    private final AtomicBoolean lock = new AtomicBoolean(false);

    public AtomicLogicTask(IntegrationService integrationService) {
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
