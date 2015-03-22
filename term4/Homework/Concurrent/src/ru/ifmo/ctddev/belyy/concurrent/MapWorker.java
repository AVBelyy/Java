package ru.ifmo.ctddev.belyy.concurrent;

import java.util.List;
import java.util.function.Function;

public class MapWorker<T, R> implements Runnable {
    private final Function<? super T, ? extends R> f;
    private final T arg;
    private final List<R> outList;
    private final int index;

    public MapWorker(Function<? super T, ? extends R> f, T arg, List<R> outList, int index) {
        this.f = f;
        this.arg = arg;
        this.outList = outList;
        this.index = index;
    }

    @Override
    public void run() {
        R value = f.apply(arg);

        synchronized (outList) {
            outList.set(index, value);
        }
    }
}
