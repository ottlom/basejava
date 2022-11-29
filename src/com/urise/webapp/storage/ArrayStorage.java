package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Array ran out of space");
        } else if (getIndex(r.getUuid()) > -1) {
            System.out.println("List's already have resume with uuid = " + r.getUuid());
        } else {
            storage[size] = r;
            System.out.println("resume " + r.getUuid() + " was added");
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        return index != -1 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("You try delete element such not found in list");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("You try update nonexistent resume");
        } else {
            storage[index] = resume;
            System.out.println("resume " + resume.getUuid() + " was update");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        int returnIndex = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                returnIndex = i;
            }
        }
        return returnIndex;
    }
}
