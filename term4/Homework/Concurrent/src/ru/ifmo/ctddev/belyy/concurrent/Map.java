package ru.ifmo.ctddev.belyy.concurrent;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Worker that applies some function to each list's element.
 *
 * @param <T> type of argument.
 * @param <R> type of result.
 * @see ru.ifmo.ctddev.belyy.concurrent.Worker
 */
public class Map<T, R> extends LazyWorker<List<R>> {
    private List<? extends T> list;
    private Function<? super T, ? extends R> function;

    public Map(List<? extends T> list, Function<? super T, ? extends R> function) {
        super();
        this.list = list;
        this.function = function;
    }

    @Override
    public List<R> calcResult() {
        return list.stream()
                   .map(function)
                   .collect(Collectors.toList());
    }

    @Override
    public List<R> mergeResults(List<List<R>> results) {
        return new ConcatList<>(results).getResult();
    }
}
