package net.education.kyivstar.benchmark.starter;

import net.education.kyivstar.benchmark.LogicExecutionStarter;
import net.education.kyivstar.benchmark.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinPoolStarter extends LogicExecutionStarter {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorStarter.class);

    @Override
    protected void performLogic(Task logic) {

        var forkJoinPool = new ForkJoinPool(NUMBER_OF_THREADS);
        var task = new RecursiveTask(logic, NUMBER_OF_TASKS);

        forkJoinPool.invoke(task);
    }

    private static class RecursiveTask extends RecursiveAction {
        private final Task logic;
        private final int tasksLeft;

        public RecursiveTask(Task logic, int tasksLeft) {
            this.logic = logic;
            this.tasksLeft = tasksLeft;
        }

        @Override
        protected void compute() {
            if (tasksLeft <= 1) {
                logic.execute();
            } else {
                int mid = tasksLeft / 2;
                invokeAll(new RecursiveTask(logic, mid), new RecursiveTask(logic, tasksLeft - mid));
            }
        }
    }
}

