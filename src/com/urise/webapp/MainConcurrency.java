package com.urise.webapp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final Lock LOCK1 = new ReentrantLock();
    private static final Lock LOCK2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
           LOCK1.lock();
           //даю второму потоку время наверняка захватить монитор LOCK2
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LOCK2.lock();
            System.out.println("some work from " + Thread.currentThread().getName());

            LOCK1.unlock();
            LOCK2.unlock();
        });

        Thread thread2 = new Thread(() -> {
            LOCK2.lock();
            LOCK1.lock();
            System.out.println("some work from " + Thread.currentThread().getName());

            LOCK2.unlock();
            LOCK1.unlock();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
