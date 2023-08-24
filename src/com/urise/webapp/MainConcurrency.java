package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainConcurrency {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            doSynchronize(LOCK1, LOCK2);
        });

        Thread thread2 = new Thread(() -> {
            doSynchronize(LOCK2, LOCK1);
        });

        //thread1.start();
        //thread2.start();

        thread1.join();
        thread2.join();

        minValue(new int[]{1, 9, 2, 7, 8, 5, 3, 4, 9, 5, 2, 1, 3});

        List<Integer> list = new ArrayList<>(Arrays.asList(1,4,3,5));
        System.out.println(list);
        oddOrEven(list);
        System.out.println(list);
    }

    public static void doSynchronize(Object lock1, Object lock2) {
        synchronized (lock1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                System.out.println("some work from " + Thread.currentThread().getName());
            }
        }
    }

    public static int minValue(int[] values) {
        int resultNum = Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (result, num) -> result * 10 + num);
        System.out.println(resultNum);
        return resultNum;
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        integers.removeAll(integers.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.partitioningBy(i -> i % 2 == 0),
                        map -> map.get(integers.stream().mapToInt(Integer::intValue).sum() % 2 == 0)
                )));
        return integers;
    }
}
