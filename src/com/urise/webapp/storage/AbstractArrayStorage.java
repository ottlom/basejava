package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

//    @Override
//    protected void doSave(Object r) {
//        addResume(r, getIndex(r.getUuid()));
//        size++;
//    }
//
//    @Override
//    protected void doDelete(Object r) {
//        removeResume(getIndex(r.getUuid()));
//        storage[size - 1] = null;
//        size--;
//    }
//
//    @Override
//    protected void doUpdate(Object r) {
//        storage[getIndex(r.getUuid())] = r;
//    }
//
//    @Override
//    protected Resume doGet(Object r) {
//        return storage[getIndex(r.getUuid())];
//    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void addResume(Resume r, int index);

    protected abstract void removeResume(int index);
}