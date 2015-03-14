package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.Comparator;
import java.util.List;

/**
 * Created by anton on 14/03/15.
 */
public class Minimum<T> implements Worker<T> {
    private List<T> list;
    private Comparator<T> comparator;
    private T minimum;

    public Minimum(List<T> list, Comparator<T> comparator) {
        this.list = list;
        this.comparator = comparator;
    }

    @Override
    public void run() {
        T minElem = list.get(0);

        for (T elem : list) {
            if (comparator.compare(minElem, elem) > 0) {
                minElem = elem;
            }
        }

        // TODO : synchronized ?
        minimum = minElem;
    }

    @Override
    public T getResult() {
        return minimum;
    }

    @Override
    public T mergeResults(List<T> results) {
        Worker<T> worker = new Minimum<>(results, comparator);
        worker.run();
        return worker.getResult();
    }
}
