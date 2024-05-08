package net.education.kyivstar.benchmark;

public class OneThread implements Runnable{
    private final BusinessLogic businessLogic;

    public OneThread(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void run() {
        businessLogic.startIntegration();
    }

}
