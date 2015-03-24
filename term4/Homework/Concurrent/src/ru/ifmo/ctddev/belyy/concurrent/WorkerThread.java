package ru.ifmo.ctddev.belyy.concurrent;

import java.util.Queue;

public class WorkerThread extends Thread {
    private final Queue<Runnable> pool;

    public WorkerThread(Queue<Runnable> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        Runnable task;

        while (!Thread.interrupted()) {
            synchronized (pool) {
                task = pool.poll();
            }

            if (task != null) {
                task.run();
            }
        }
    }
}
