package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.Logic;
import net.education.kyivstar.benchmark.Starter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

public class ExecutorStarter extends Starter {
    @Override
    protected void fulfilment(Logic logic) {
        int numberOfTasks = 100;

        var executorService = Executors.newFixedThreadPool(10);
        var completionService = new ExecutorCompletionService<>(executorService);
        var latch = new CountDownLatch(numberOfTasks);

        for (int x = 0; x < numberOfTasks; x++) {
            completionService.submit(() -> {
                try {
                    logic.execute();
                } finally {
                    latch.countDown();
                }
                return null;
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        executorService.shutdown();
    }
}
