package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.Logic;
import net.education.kyivstar.benchmark.Starter;

public class OneThreadStarter extends Starter {
    @Override
    protected void fulfilment(Logic logic) {
        for (int x = 0; x < 100; x++) {
            logic.execute();
        }
    }
}
