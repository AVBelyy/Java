package ru.ifmo.ctddev.belyy.concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * My very own thread pool.
 */
public class FixedThreadPool {
    private final Queue<Runnable> pool;
    private final List<Thread> workerThreads;

    public FixedThreadPool(int threads) {
        pool = new LinkedList<>();
        workerThreads = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            Thread thread = new WorkerThread(pool);
            workerThreads.add(thread);
            thread.start();
        }
    }

    public void execute(Runnable task) {
        synchronized (pool) {
            pool.add(task);
        }
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : workerThreads) {
            thread.join();
        }
    }
}
