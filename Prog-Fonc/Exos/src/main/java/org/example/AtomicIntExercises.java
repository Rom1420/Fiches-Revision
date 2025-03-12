package org.example;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntExercises {
    public static void add(AtomicInteger a, AtomicInteger b){
        a.addAndGet(b.get());
    }

    public static boolean compareAndSet(AtomicInteger atomic, int expected, int newValue){
        return atomic.compareAndSet(expected, newValue);
    }

    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    public void increment(){
        atomicInteger.addAndGet(2);
    }
    public int getValue(){
        return atomicInteger.get();
    }
    public static int sumWithAtomic(List<Integer> list){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        list.forEach(atomicInteger::addAndGet);
        return atomicInteger.get();
    }

    public static int countEven(List<Integer> list) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        list.stream().filter(x -> x % 2 == 0).forEach(x -> atomicInteger.incrementAndGet());
        return atomicInteger.get();
    }

    public static int product(int[] array) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        IntStream.of(array).forEach(n -> atomicInteger.updateAndGet(v -> n *v));
        return atomicInteger.get();
    }

    return value == null ? empty () : of
}
