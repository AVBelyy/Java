package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.List;

/**
 * Created by anton on 14/03/15.
 */
public class Concat<T> implements Worker<String> {
    private Map<T, String> mapWorker;
    private String string;

    public Concat(List<T> list) {
        this.mapWorker = new Map<>(list, T::toString);
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();

        mapWorker.run();
        List<String> elemStrings = mapWorker.getResult();

        for (String elemString : elemStrings) {
            stringBuilder.append(elemString);
        }

        string = stringBuilder.toString();
    }

    @Override
    public String getResult() {
        return string;
    }

    @Override
    public String mergeResults(List<String> results) {
        Worker<String> worker = new Concat<>(results);
        worker.run();
        return worker.getResult();
    }
}
