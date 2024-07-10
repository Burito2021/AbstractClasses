package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;

public class OneThreadStarter extends LogicExecutionStarter {
    @Override
    protected void performLogic(Task logic) {
        for (int x = 0; x < 100; x++) {
            logic.execute();
        }
    }
}
