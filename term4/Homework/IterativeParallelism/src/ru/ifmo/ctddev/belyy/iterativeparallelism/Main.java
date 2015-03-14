package ru.ifmo.ctddev.belyy.iterativeparallelism;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        IterativeParallelism.maximum(4, numbers, Comparator.<Integer>naturalOrder());
    }
}
