package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

public class ExecutorStarter extends LogicExecutionStarter {
    @Override
    protected void performLogic(Task logic) {
        int numberOfTasks = 100;

        var executorService = Executors.newFixedThreadPool(10);
        List<Callable<Void>> tasks = new ArrayList<>(100);

        for (int x = 0; x < numberOfTasks; x++) {
            tasks.add(() -> {
                logic.execute();
                return null;
            });
        }
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
