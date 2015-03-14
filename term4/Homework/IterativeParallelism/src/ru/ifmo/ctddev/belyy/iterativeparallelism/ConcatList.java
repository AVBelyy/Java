package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 14/03/15.
 */
public class ConcatList<T> implements Worker<List<T>> {
    private List<List<T>> lists;
    private List<T> resultList;

    public ConcatList(List<List<T>> lists) {
        this.lists = lists;
    }

    @Override
    public void run() {
        List<T> result = new ArrayList<>();

        for (List<T> list : lists) {
            result.addAll(list);
        }

        resultList = result;
    }

    @Override
    public List<T> getResult() {
        return resultList;
    }

    @Override
    public List<T> mergeResults(List<List<T>> results) {
        Worker<List<T>> worker = new ConcatList<>(results);
        worker.run();
        return worker.getResult();
    }
}
