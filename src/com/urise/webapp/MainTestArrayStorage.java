package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractArrayStorage;
import com.urise.webapp.storage.SortedArrayStorage;


public class MainTestArrayStorage {
    static final AbstractArrayStorage storage = new SortedArrayStorage();

    public static void main(String[] args) {

        Resume r1 = new Resume("c");
        Resume r2 = new Resume("b");

        Resume r3 = new Resume("a");

        storage.save(r1);
        storage.save(r2);
        storage.save(r3);

        printAll();
        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        System.out.println("Size: " + storage.size());

        System.out.println("Get dummy: " + storage.get("dummy"));

        printAll();
        storage.delete(r1.getUuid());
        printAll();
        storage.clear();
        printAll();

        System.out.println("Size: " + storage.size());
        storage.save(r1);
        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        storage.update(r1);
        System.out.println("Get r1: " + storage.get(r1.getUuid()));
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : storage.getAll()) {
            System.out.println(r);
        }
    }
}
