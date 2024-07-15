package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;
import net.education.kyivstar.benchmark.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorStarter extends LogicExecutionStarter {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorStarter.class);

    @Override
    protected void performLogic(Task logic) {

        var executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        var tasks = new ArrayList<Callable<Void>>(NUMBER_OF_TASKS);

        for (int x = 0; x < NUMBER_OF_TASKS; x++) {
            tasks.add(() -> {
                logic.execute();
                return null;
            });
        }
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error: ", e);
        } finally {
            Utils.shutdownExecutor(executorService);
        }
    }
}
