package net.education.kyivstar.benchmark;

import java.util.UUID;

public class SynchronizationLock implements Runnable{
    private final BusinessLogic businessLogic;
    private final Object lock = new Object();

    public SynchronizationLock(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void run() {
        synchronized (lock) {
            businessLogic.startIntegration();
        }
    }
}
