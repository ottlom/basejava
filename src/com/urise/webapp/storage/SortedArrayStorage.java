package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void addResume(Resume r, int index) {
        int addIndex = -index - 1;
        System.arraycopy(storage, addIndex, storage, addIndex + 1, size - addIndex);
        storage[addIndex] = r;
    }

    @Override
    protected void removeResume(int index) {
        int removeIndex = size - index - 1;
        if (removeIndex > 0) {
            System.arraycopy(storage, index + 1, storage, index, removeIndex);
        }
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

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
