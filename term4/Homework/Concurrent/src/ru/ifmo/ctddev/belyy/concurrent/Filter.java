package ru.ifmo.ctddev.belyy.concurrent;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Worker that removes list elements that do not satisfy some predicate.
 *
 * @param <T> type parameter.
 * @see ru.ifmo.ctddev.belyy.concurrent.Worker
 */
public class Filter<T> extends LazyWorker<List<T>> {
    private List<? extends T> list;
    private Predicate<? super T> predicate;

    public Filter(List<? extends T> list, Predicate<? super T> predicate) {
        super();
        this.list = list;
        this.predicate = predicate;
    }

    @Override
    public List<T> calcResult() {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public List<T> mergeResults(List<List<T>> results) {
        Worker<List<T>> worker = new ConcatList<>(results);
        worker.run();
        return worker.getResult();
    }
}
