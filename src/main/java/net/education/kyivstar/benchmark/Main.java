package net.education.kyivstar.benchmark;

import net.education.kyivstar.benchmark.starter.ExecutorStarter;
import net.education.kyivstar.benchmark.starter.OneThreadStarter;

public class Main {
    public static void main(String[] args) {
        var oneThreadStarter = new OneThreadStarter();
        oneThreadStarter.start();

        var executorStarter = new ExecutorStarter();
        executorStarter.start();
    }
}