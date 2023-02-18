package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.storage.SortedArrayStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainArray {
    private final static AbstractStorage storage = new SortedArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | update uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String name = null;
            if (params.length == 3) {
                uuid = params[1].intern();
                name = params[2].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(storage.size());
                    break;
                case "save":
                    r = new Resume(uuid,name);
                    storage.save(r);
                    printAll();
                    break;
                case "update":
                    r = new Resume(uuid,name);
                    storage.update(r);
                    printAll();
                    break;
                case "delete":
                    storage.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(storage.get(uuid));
                    break;
                case "clear":
                    storage.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        System.out.println("----------------------------");

        for (Resume resume : storage.getAllSorted()) {
            System.out.println(resume);
        } if (storage.getAllSorted().size() == 0) {
            System.out.println("Empty");
        }
        System.out.println("----------------------------");
    }
}
