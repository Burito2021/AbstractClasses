package net.education.kyivstar.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        //oneThread
        var businessLogic = new BusinessLogic();
        var oneThread = new OneThread(businessLogic);
        Map<String, Long> durations = new HashMap<>();

        long startTimeOneThread = System.nanoTime();

        for (int x = 0; x < 100; x++) {
            oneThread.run();
        }

        long endTimeOneThread = System.nanoTime();
        long duration = (endTimeOneThread - startTimeOneThread);
        durations.put(oneThread.getName(), nanosToMillis(duration));

        System.out.println(durations);

        ///Synchronized
        var sync = new Synchronized(businessLogic);

        long startTimeSynchronized = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        executorService.submit(sync);
        for (int x = 0; x < 100; x++) {
            executorService.submit(sync);
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
        }

        long endTimeSynchronized = System.nanoTime();
        long durationSynchronized = (endTimeSynchronized - startTimeSynchronized);
        durations.put(sync.getName()+" MultiTread", nanosToMillis(durationSynchronized));

        System.out.println(durations);

        //oneThreadSynchronized
        var syncOneThreadSynchronized = new Synchronized(businessLogic);

        long startTimeOneThreadSynchronized = System.nanoTime();

        for (int x = 0; x < 100; x++) {
            syncOneThreadSynchronized.run();
        }

        long endTimeOneThreadSynchronized = System.nanoTime();
        long durationOneThreadSynchronized = (endTimeOneThreadSynchronized - startTimeOneThreadSynchronized);
        durations.put(syncOneThreadSynchronized.getName() + " One Thread", nanosToMillis(durationOneThreadSynchronized));

        System.out.println(durations);

        ///SynchronizationLock
        long startTimeSynch = System.nanoTime();
        ExecutorService executorServiceSynch = Executors.newFixedThreadPool(100);
        SynchronizationLock synchronizationLock = new SynchronizationLock(businessLogic);
        for (int x = 0; x < 100; x++) {
            executorServiceSynch.submit(synchronizationLock);
        }

        executorServiceSynch.shutdown();

        while (!executorServiceSynch.isTerminated()) {
        }

        long endTimeSynch = System.nanoTime();
        long durationSynch = (endTimeSynch - startTimeSynch);
        durations.put(synchronizationLock.getName()+" MultiTread", nanosToMillis(durationSynch));

        System.out.println(durations);

        //oneThreadSynchronizationLock
        var synchronizationLockOneThread = new SynchronizationLock(businessLogic);
        long startTimeOneThreadSynchronizationLock = System.nanoTime();

        for (int x = 0; x < 100; x++) {
            synchronizationLockOneThread.run();
        }

        long endTimeOneThreadSynchronizationLock = System.nanoTime();
        long durationOneThreadSynchronizationLock = (endTimeOneThreadSynchronizationLock - startTimeOneThreadSynchronizationLock);
        durations.put(synchronizationLockOneThread.getName() + " One Thread", nanosToMillis(durationOneThreadSynchronizationLock));

        System.out.println(durations);
        ///Atomic
        long startTimeAtomic = System.nanoTime();
        ExecutorService executorServiceAtomic = Executors.newFixedThreadPool(100);
        var atomicThread = new AtomicThread(businessLogic);
        for (int x = 0; x < 100; x++) {
            executorServiceAtomic.submit(atomicThread);
        }

        executorServiceAtomic.shutdown();

        while (!executorService.isTerminated()) {
        }

        long endTimeAtomic = System.nanoTime();
        long durationAtomic = (endTimeAtomic - startTimeAtomic);
        durations.put(atomicThread.getName()+" MultiTread", nanosToMillis(durationAtomic));

        System.out.println(durations);

        //oneThreadAtomic
        var atomicOneThread = new AtomicThread(businessLogic);
        long startTimeOneThreadAtomic = System.nanoTime();

        for (int x = 0; x < 100; x++) {
            atomicOneThread.run();
        }

        long endTimeOneThreadAtomic = System.nanoTime();
        long durationOneThreadAtomic = (endTimeOneThreadAtomic - startTimeOneThreadAtomic);
        durations.put(atomicOneThread.getName() + " One Thread", nanosToMillis(durationOneThreadAtomic));

        System.out.println(durations);
    }

    public static long nanosToMillis(long nanoseconds) {
        return nanoseconds / 1_000_000;
    }
}