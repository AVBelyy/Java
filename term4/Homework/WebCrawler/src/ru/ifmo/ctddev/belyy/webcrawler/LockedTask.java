package ru.ifmo.ctddev.belyy.webcrawler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker wrapper that resets lock after the wrapped job's done.
 */
public class LockedTask implements ExceptionableRunnable {
    private AtomicInteger v;
    private ExceptionableRunnable runnable;

    public LockedTask(AtomicInteger v, ExceptionableRunnable runnable) {
        this.v = v;
        this.runnable = runnable;
    }

    @Override
    public void run() throws Throwable {
        try {
            runnable.run();
        } finally {
            v.set(0);
        }
    }

    @Override
    public void onError(Throwable e) {
        runnable.onError(e);
    }
}
