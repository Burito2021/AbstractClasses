package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorStarter extends LogicExecutionStarter {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorStarter.class);

    @Override
    protected void performLogic(Task logic) {
        int numberOfTasks = 100;

        var executorService = Executors.newFixedThreadPool(10);
        List<Callable<Void>> tasks = new ArrayList<>(numberOfTasks);
        List<Callable<Void>> t = new ArrayList<>(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            t.add(() -> {
                logic.execute();
                return null;
            });
        }

        for (int x = 0; x < numberOfTasks; x++) {
            tasks.add(() -> {
                logic.execute();
                return null;
            });
        }
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Error " + e);
        } finally {
            executorService.shutdown();
        }
    }
}
