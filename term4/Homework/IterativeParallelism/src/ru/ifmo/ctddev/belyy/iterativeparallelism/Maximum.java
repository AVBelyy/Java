package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.Comparator;
import java.util.List;

/**
 * Created by anton on 14/03/15.
 */
public class Maximum<T> implements Worker<T> {
    private Minimum<T> minimumWorker;

    public Maximum(List<T> list, Comparator<T> comparator) {
        minimumWorker = new Minimum<T>(list, comparator.reversed());
    }

    @Override
    public void run() {
        minimumWorker.run();
    }

    public T getResult() {
        return minimumWorker.getResult();
    }

    @Override
    public T mergeResults(List<T> results) {
        return minimumWorker.mergeResults(results);
    }
}
