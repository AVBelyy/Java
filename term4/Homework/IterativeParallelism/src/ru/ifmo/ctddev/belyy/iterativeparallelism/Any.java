package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by anton on 14/03/15.
 */
public class Any<T> implements Worker<Boolean> {
    private All<T> allWorker;

    public Any(List<T> list, Predicate<T> predicate) {
        allWorker = new All<>(list, predicate.negate());
    }

    @Override
    public void run() {
        allWorker.run();
    }

    @Override
    public Boolean getResult() {
        return !allWorker.getResult();
    }

    @Override
    public Boolean mergeResults(List<Boolean> results) {
        Worker<Boolean> worker = new Any<>(results, Predicate.isEqual(true));
        worker.run();
        return worker.getResult();
    }
}
