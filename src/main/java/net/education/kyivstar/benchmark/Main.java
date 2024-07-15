package net.education.kyivstar.benchmark;

import net.education.kyivstar.benchmark.starter.*;

import static net.education.kyivstar.benchmark.Utils.starterRunner;

public class Main {
    public static void main(String[] args) {
        starterRunner(1, new ThreadPoolExecutorStarter());
        starterRunner(1, new PlainThreadStarter());
        starterRunner(1, new ScheduledThreadPoolExecutorStarter());
        starterRunner(1, new CompletableFutureStarter());
        starterRunner(1, new ForkJoinPoolStarter());
        starterRunner(1, new OneThreadStarter());
        starterRunner(1, new ExecutorStarter());
    }
}