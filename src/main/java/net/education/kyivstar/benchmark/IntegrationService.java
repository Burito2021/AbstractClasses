package net.education.kyivstar.benchmark;

import java.util.concurrent.TimeUnit;

public class IntegrationService {
    int amount = 0;

    public int startIntegration() {
        try {
            amount = amount + 1;
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (Exception e) {
        }
        return amount;
    }
}
