package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by anton on 14/03/15.
 */
public class All<T> implements Worker<Boolean> {
    private List<T> list;
    private Predicate<T> predicate;
    private boolean result;

    public All(List<T> list, Predicate<T> predicate) {
        this.list = list;
        this.predicate = predicate;
    }

    @Override
    public void run() {
        for (T elem : list) {
            if (!predicate.test(elem)) {
                result = false;
                break;
            }
        }
        result = true;
    }

    @Override
    public Boolean getResult() {
        return result;
    }

    @Override
    public Boolean mergeResults(List<Boolean> results) {
        Worker<Boolean> worker = new All<>(results, Predicate.isEqual(true));
        worker.run();
        return worker.getResult();
    }
}
