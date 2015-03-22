package ru.ifmo.ctddev.belyy.concurrent;

import java.util.List;

/**
 * Worker that concats string representations of list elements.
 *
 * @param <T> type parameter.
 * @see ru.ifmo.ctddev.belyy.concurrent.Worker
 * @see ru.ifmo.ctddev.belyy.concurrent.Map
 */
public class Concat<T> extends LazyWorker<String> {
    private Map<? extends T, String> mapWorker;

    public Concat(List<T> list) {
        super();
        this.mapWorker = new Map<>(list, T::toString);
    }

    @Override
    public String calcResult() {
        return mergeResults(mapWorker.getResult());
    }

    @Override
    public String mergeResults(List<String> results) {
        return results.stream()
                      .reduce("", String::concat);
    }
}
