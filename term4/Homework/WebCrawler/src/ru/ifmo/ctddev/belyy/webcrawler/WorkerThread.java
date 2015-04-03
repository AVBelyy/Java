package ru.ifmo.ctddev.belyy.webcrawler;

import java.util.Queue;

/**
 * Worker thread for FixedThreadPool.
 * Polls jobs from task queue and executes one if present.
 */
public class WorkerThread extends Thread {
    private final Queue<ExceptionableRunnable> pool;

    public WorkerThread(Queue<ExceptionableRunnable> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        ExceptionableRunnable task;

        while (!Thread.interrupted()) {
            synchronized (pool) {
                task = pool.poll();
            }

            if (task != null) {
                try {
                    task.run();
                } catch (Throwable e) {
                    task.onError(e);
                }
            }
        }
    }
}
