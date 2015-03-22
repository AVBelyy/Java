package ru.ifmo.ctddev.belyy.concurrent;

import java.util.List;
import java.util.function.Function;

/**
 * ParallelMapper interface.
 *
 * @see java.lang.AutoCloseable
 */
public interface ParallelMapper extends AutoCloseable {
    <T, R> List<R> run(
            Function<? super T, ? extends R> f,
            List<? extends T> args
    ) throws InterruptedException;

    @Override
    void close() throws InterruptedException;
}