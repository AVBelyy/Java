package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by anton on 14/03/15.
 */
public class Map<T, R> implements Worker<List<R>> {
    private List<T> list;
    private List<R> resultList;
    private Function<T, R> function;

    public Map(List<T> list, Function<T, R> function) {
        this.list = list;
        this.function = function;
        this.resultList = new ArrayList<>();
    }

    @Override
    public void run() {
        for (T elem : list) {
            resultList.add(function.apply(elem));
        }
    }

    @Override
    public List<R> getResult() {
        return resultList;
    }

    @Override
    public List<R> mergeResults(List<List<R>> results) {
        Worker<List<R>> worker = new ConcatList<>(results);
        worker.run();
        return worker.getResult();
    }
}
