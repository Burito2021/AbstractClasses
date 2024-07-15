package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static net.education.kyivstar.benchmark.Utils.shutdownExecutor;

public class CompletableFutureStarter extends LogicExecutionStarter {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureStarter.class);

    @Override
    protected void performLogic(Task logic) {

        var executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        var tasks = new ArrayList<CompletableFuture<Void>>(NUMBER_OF_TASKS);

        for (int x = 0; x < NUMBER_OF_TASKS; x++) {
            tasks.add(CompletableFuture.runAsync(
                    () -> {
                        logic.execute();
                    }, executorService));
        }

        var allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));

        try {
            allTasks.get();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            logger.error("Error: ", e);
        } finally {
            shutdownExecutor(executorService);
        }
    }

}
