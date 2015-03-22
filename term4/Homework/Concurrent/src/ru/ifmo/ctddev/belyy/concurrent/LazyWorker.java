package ru.ifmo.ctddev.belyy.concurrent;

import java.util.Optional;

/**
 * Worker interface implementation with memoization of result.
 *
 * @param <R> result type.
 * @see ru.ifmo.ctddev.belyy.concurrent.Worker
 */
public abstract class LazyWorker<R> implements Worker<R> {
    private Optional<R> result;

    public LazyWorker() {
        result = Optional.empty();
    }

    public void run() {
        getResult();
    }

    public R getResult() {
        if (!result.isPresent()) {
            result = Optional.of(calcResult());
        }

        return result.get();
    }

    /**
     * Returns the result of thread's work.
     *
     * @return result of {@link java.lang.Runnable#run() run()} method.
     */
    protected abstract R calcResult();
}