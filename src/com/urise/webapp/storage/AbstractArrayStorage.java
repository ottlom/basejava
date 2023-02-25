package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        if (size > STORAGE_LIMIT) {
            throw new StorageException("Storage overflow",resume.getUuid());
        }
        addResume(resume, (Integer) getSearchKey(resume.getUuid()));
        size++;
    }

    @Override
    protected void doDelete(Object r) {
        removeResume((Integer) r);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(Integer) getSearchKey(resume.getUuid())] = resume;
    }

    @Override
    protected Resume doGet(Object r) {
        return storage[(Integer) r];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected List<Resume> getCopyStorage() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void addResume(Resume r, int index);

    protected abstract void removeResume(int index);
}