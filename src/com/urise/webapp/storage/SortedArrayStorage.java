package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

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

    private static final Comparator<Resume> RESUME_UUID_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid,"filler");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_UUID_COMPARATOR);
    }
}
