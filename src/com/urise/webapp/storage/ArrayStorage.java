package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addResume(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        for (int i = 0; i < size; i++) {
            if (searchKey.equals(storage[i])) {
                return searchKey;
            }
        }
        return null;
    }

    @Override
    protected void doSave(Object searchKey) {

    }

    @Override
    protected void doDelete(Object searchKey) {

    }

    @Override
    protected void doUpdate(Object searchKey) {

    }

    @Override
    protected Resume doGet(Object searchKey) {
        return null;
    }
}
