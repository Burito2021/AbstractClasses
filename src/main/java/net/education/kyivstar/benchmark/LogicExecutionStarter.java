package net.education.kyivstar.benchmark;

import net.education.kyivstar.benchmark.logic.AtomicLogicTask;
import net.education.kyivstar.benchmark.logic.SynLockLogicTask;
import net.education.kyivstar.benchmark.logic.SynLogicTask;
import net.education.kyivstar.benchmark.logic.WithoutLogicTask;

import java.util.HashMap;
import java.util.Map;

import static net.education.kyivstar.benchmark.Utils.nanosToMillis;

public abstract class LogicExecutionStarter {

    public void runServices() {
        var intService = new IntegrationService();

        Map<String, Long> durations = new HashMap<>();

        durations.put("OneThread ", nanosToMillis(measureExecutionTime(new WithoutLogicTask(intService))));

        durations.put("SynLogicTask ", nanosToMillis(measureExecutionTime(new SynLogicTask(intService))));

        durations.put("SynLockLogicTask ", nanosToMillis(measureExecutionTime(new SynLockLogicTask(intService))));

        durations.put("AtomicLogicTask ", nanosToMillis(measureExecutionTime(new AtomicLogicTask(intService))));
        System.out.print("Start execution -->");
        System.out.println(durations);
    }

    protected long measureExecutionTime(Task logic) {
        long startTimeOneThread = System.nanoTime();

        performLogic(logic);

        long endTimeOneThread = System.nanoTime();
        long duration = (endTimeOneThread - startTimeOneThread);

        return duration;
    }

    protected abstract void performLogic(Task logic);
}
