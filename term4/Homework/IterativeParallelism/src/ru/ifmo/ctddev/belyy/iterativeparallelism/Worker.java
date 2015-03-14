package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.List;

/**
 * Created by anton on 14/03/15.
 */
public interface Worker<R> extends Runnable {
    R getResult();
    R mergeResults(List<R> results);
}
