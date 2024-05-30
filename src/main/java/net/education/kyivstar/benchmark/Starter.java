package net.education.kyivstar.benchmark;

import net.education.kyivstar.benchmark.logic.AtomicLogicService;
import net.education.kyivstar.benchmark.logic.SynLockLogicService;
import net.education.kyivstar.benchmark.logic.SynLogicService;
import net.education.kyivstar.benchmark.logic.WithoutLogicService;

import java.util.HashMap;
import java.util.Map;

import static net.education.kyivstar.benchmark.Utils.nanosToMillis;

public abstract class Starter {

    public void start() {
        var intService = new IntegrationService();

        Map<String, Long> durations = new HashMap<>();

        durations.put("OneThread ", nanosToMillis(execute(new WithoutLogicService(intService))));

        durations.put("SynLogicService ", nanosToMillis(execute(new SynLogicService(intService))));

        durations.put("SynLockLogicService ", nanosToMillis(execute(new SynLockLogicService(intService))));

        durations.put("AtomicLogicService ", nanosToMillis(execute(new AtomicLogicService(intService))));
        System.out.print("Start multithreading execution -->");
        System.out.println(durations);
    }

    protected long execute(Logic logic) {
        long startTimeOneThread = System.nanoTime();

        fulfilment(logic);

        long endTimeOneThread = System.nanoTime();
        long duration = (endTimeOneThread - startTimeOneThread);

        return duration;
    }

    protected abstract void fulfilment(Logic logic);
}
