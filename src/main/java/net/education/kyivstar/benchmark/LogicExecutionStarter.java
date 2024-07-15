package net.education.kyivstar.benchmark;

import net.education.kyivstar.benchmark.logic.AtomicLogicTask;
import net.education.kyivstar.benchmark.logic.SynLockLogicTask;
import net.education.kyivstar.benchmark.logic.SynLogicTask;
import net.education.kyivstar.benchmark.logic.WithoutLogicTask;
import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.education.kyivstar.benchmark.Utils.nanosToMillis;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)

public abstract class LogicExecutionStarter {

    private static final Logger logger = LoggerFactory.getLogger(LogicExecutionStarter.class);
    protected static int NUMBER_OF_TASKS = 100;
    protected static int NUMBER_OF_THREADS = 10;

    public void runServices() {
        var intService = new IntegrationService();

        Map<String, Long> durations = new HashMap<>();
        durations.put("OneThread ", nanosToMillis(measureExecutionTime(new WithoutLogicTask(intService))));

        durations.put("SynLogicTask ", nanosToMillis(measureExecutionTime(new SynLogicTask(intService))));

        durations.put("SynLockLogicTask ", nanosToMillis(measureExecutionTime(new SynLockLogicTask(intService))));

        durations.put("AtomicLogicTask ", nanosToMillis(measureExecutionTime(new AtomicLogicTask(intService))));
        // Print table
        logger.info("| " + getClass().getSimpleName() + "|" + "{}", durations);

        logger.info("---------------------------------------------------------------------------");

//        // Print table
//        logger.info("-------------------------------------------------------");
//        logger.info("|{}", getClass().getSimpleName()+"|");
//        logger.info("-------------------------------------------------------");
//        logger.info("| {} | {} |", "Task    ", "Duration (ms)");
//        logger.info("|--------------|------------|");
//
//        durations.forEach((task, duration) ->
//                logger.info("| {} | {} |", task, duration));
//
//        logger.info("-------------------------------------------------------");
    }

    @Benchmark
    protected long measureExecutionTime(Task logic) {
        long startTimeOneThread = System.nanoTime();

        performLogic(logic);

        long endTimeOneThread = System.nanoTime();

        return (endTimeOneThread - startTimeOneThread);
    }

    protected abstract void performLogic(Task logic);
}