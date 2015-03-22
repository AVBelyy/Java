package ru.ifmo.ctddev.belyy.concurrent;

public class Subtask implements Runnable {
    private Counter counter;
    private Runnable runnable;

    public Subtask(Counter counter, Runnable runnable) {
        this.counter = counter;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            runnable.run();
        } finally {
            counter.increment();
        }
    }
}
