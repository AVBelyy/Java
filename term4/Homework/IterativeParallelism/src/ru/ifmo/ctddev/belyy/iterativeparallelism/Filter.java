package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by anton on 14/03/15.
 */
public class Filter<T> implements Worker<List<T>> {
    private List<T> list;
    private List<T> filteredList;
    private Predicate<T> predicate;

    public Filter(List<T> list, Predicate<T> predicate) {
        this.list = list;
        this.predicate = predicate;
        this.filteredList = new ArrayList<>();
    }

    @Override
    public void run() {
        for (T elem : list) {
            if (predicate.test(elem)) {
                filteredList.add(elem);
            }
        }
    }

    @Override
    public List<T> getResult() {
        return filteredList;
    }

    @Override
    public List<T> mergeResults(List<List<T>> results) {
        Worker<List<T>> worker = new ConcatList<>(results);
        worker.run();
        return worker.getResult();
    }
}
