package net.education.kyivstar.benchmark;

import net.education.kyivstar.benchmark.starter.CompletableFutureStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureStarter.class);

    public static long nanosToMillis(long nanoseconds) {

        return nanoseconds / 1_000_000;
    }

    public static void starterRunner(int numberOftTimes, LogicExecutionStarter logic) {
        for (int x = 0; x < numberOftTimes; x++) {
            logic.runServices();
        }
    }

    public static void shutdownExecutor(ExecutorService executorService) {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            logger.error("Error: ", e);
        }
    }
}
