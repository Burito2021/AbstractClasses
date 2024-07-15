package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class PlainThreadStarter extends LogicExecutionStarter {
    private static final Logger logger = LoggerFactory.getLogger(PlainThreadStarter.class);

    @Override
    protected void performLogic(Task logic) {
        var tasks = new ArrayList<Thread>(NUMBER_OF_TASKS);

        for (int x = 0; x < NUMBER_OF_TASKS; x++) {

            tasks.add(new Thread(logic::execute));
        }
        try {
            tasks.forEach(Thread::start);
            tasks.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Error: " + e);
                }
            });
        } catch (Exception e) {
            logger.error("Error: " + e);
        }
    }
}
