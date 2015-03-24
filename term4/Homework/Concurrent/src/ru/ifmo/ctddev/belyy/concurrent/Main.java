package ru.ifmo.ctddev.belyy.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Tester for IterativeParallelism class.
 *
 * @see ru.ifmo.ctddev.belyy.concurrent.IterativeParallelism
 */
public class Main {
    /**
     * Entry point of the program.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        try (ParallelMapper mapper = new ParallelMapperImpl(4)) {
            IterativeParallelism ip = new IterativeParallelism(mapper);
            try {
                ip.maximum(4, numbers, Comparator.<Integer>naturalOrder());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}