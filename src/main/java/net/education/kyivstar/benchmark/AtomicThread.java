package net.education.kyivstar.benchmark;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicThread implements Runnable {
    private final BusinessLogic businessLogic;

    private final AtomicBoolean lock = new AtomicBoolean(false);

    public AtomicThread(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void run() {
        if (!lock.compareAndSet(false, true)) {
            return;
        }
        try {
            businessLogic.startIntegration();
        } finally {
            lock.set(false);
        }

    }
}
