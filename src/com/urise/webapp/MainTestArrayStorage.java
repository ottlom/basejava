package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serialization.DataStreamSerializer;

import java.util.List;


public class MainTestArrayStorage {
    static final AbstractStorage storage = new PathStorage("C:\\Java\\projects\\basejava\\storage", new DataStreamSerializer());

    public static void main(String[] args) {

        Resume r1 = new Resume("1","Ivanov Ivan");
        Resume r2 = new Resume("2","Nemkov Vladimir");
        Resume r3 = new Resume("3","Khabib Nurmagomedov");


        storage.save(r1);
        storage.save(r2);
        storage.save(r3);

        printAll();
        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        System.out.println("Size: " + storage.size());

        //System.out.println("Get dummy: " + storage.get("dummy")); //throw NotExistStorageException

        printAll();
        storage.delete(r1.getUuid());
        printAll();
        storage.clear();
        printAll();

        System.out.println("Size: " + storage.size());
        storage.save(r1);
        System.out.println("Size: " + storage.size());
        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        storage.update(r1);
        System.out.println("Get r1: " + storage.get(r1.getUuid()));
    }

    static void printAll() {
        System.out.println("\nGet All");
        List<Resume> all = storage.getAllSorted();
        for (Resume r : all) {
            System.out.println(r);
        }
    }
}
