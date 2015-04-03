package ru.ifmo.ctddev.belyy.webcrawler;

public interface ExceptionableRunnable {
    void run() throws Throwable;
    void onError(Throwable e);
}
