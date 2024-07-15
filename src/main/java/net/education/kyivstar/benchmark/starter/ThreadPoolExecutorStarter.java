package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static net.education.kyivstar.benchmark.Utils.shutdownExecutor;

public class ThreadPoolExecutorStarter extends LogicExecutionStarter {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorStarter.class);

    @Override
    protected void performLogic(Task logic) {

        var threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_THREADS,
                NUMBER_OF_THREADS,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        var tasks = new ArrayList<Callable<Void>>(NUMBER_OF_TASKS);

        for (int x = 0; x < NUMBER_OF_TASKS; x++) {
            tasks.add(() -> {
                logic.execute();
                return null;
            });
        }
        try {
            threadPoolExecutor.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error: ", e);
        } finally {
            shutdownExecutor(threadPoolExecutor);
        }
    }


}
