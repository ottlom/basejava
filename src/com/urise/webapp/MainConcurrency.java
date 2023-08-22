package com.urise.webapp;

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

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
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
}
