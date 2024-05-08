package net.education.kyivstar.benchmark;


public class Synchronized implements Runnable {
    private final BusinessLogic businessLogic;

    public Synchronized(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    @Override
    public synchronized void run() {
        businessLogic.startIntegration();
    }

    public String getName() {
        return getClass().getSimpleName();
    }


}
