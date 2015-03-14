package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by anton on 14/03/15.
 */
public class IterativeParallelism {
    public static <T> T minimum(int threads, List<T> list, Comparator<T> comparator) {
        return parallelize(threads, list, job -> new Minimum<>(job, comparator));
    }

    public static <T> T maximum(int threads, List<T> list, Comparator<T> comparator) {
        return parallelize(threads, list, job -> new Maximum<>(job, comparator));
    }

    public static <T> boolean all(int threads, List<T> list, Predicate<T> predicate) {
        return parallelize(threads, list, job -> new All<>(job, predicate));
    }

    public static <T> boolean any(int threads, List<T> list, Predicate<T> predicate) {
        return parallelize(threads, list, job -> new Any<>(job, predicate));
    }

    public static <T> List<T> filter(int threads, List<T> list, Predicate<T> predicate) {
        return parallelize(threads, list, job -> new Filter<>(job, predicate));
    }

    public static <T, R> List<R> map(int threads, List<T> list, Function<T, R> function) {
        return parallelize(threads, list, job -> new Map<>(job, function));
    }

    public static <T> String concat(int threads, List<T> list) {
        return parallelize(threads, list, job -> new Concat<>(job));
    }

    private static <T, R> R parallelize(int threads, List<T> job, Function<List<T>, Worker<R>> constructor) {
        return doInParallel(
                splitJob(threads, job)
                        .stream()
                        .map(constructor)
                        .collect(Collectors.toList())
               );
    }

    private static <R> R doInParallel(List<Worker<R>> workers) {
        // Run jobs.
        List<Thread> threads = new ArrayList<>();
        for (Worker<R> worker : workers) {
            Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Collect results.
        List<R> results = workers.stream().map(Worker::getResult).collect(Collectors.toList());

        // Merge and return results.
        return workers.get(0).mergeResults(results);
    }

    private static <T> List<List<T>> splitJob(int parts, List<T> job) {
        int jobSize = job.size();

        if (parts > jobSize) {
            // TODO : Find out what to do in this case.
            throw new IllegalArgumentException("threads > number of jobs");
        }
        if (parts < 1) {
            throw new IllegalArgumentException("threads < 1");
        }

        List<List<T>> jobs = new ArrayList<>();
        int chunkSize = jobSize / parts;

        for (int l = 0; l < jobSize; l += chunkSize) {
            int r = Math.min(l + chunkSize, jobSize);
            jobs.add(job.subList(l, r));
        }

        return jobs;
    }
}
