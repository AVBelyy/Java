package ru.ifmo.ctddev.belyy.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper worker that concats lists of objects into a signle list.
 *
 * @param <T> type parameter.
 * @see ru.ifmo.ctddev.belyy.concurrent.Worker
 */
public class ConcatList<T> extends LazyWorker<List<T>> {
    private List<List<T>> lists;

    public ConcatList(List<List<T>> lists) {
        super();
        this.lists = lists;
    }

    @Override
    public List<T> calcResult() {
        return mergeResults(lists);
    }

    @Override
    public List<T> mergeResults(List<List<T>> results) {
        List<T> result = new ArrayList<>();
        results.forEach(result::addAll);
        return result;
    }
}
