package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    static List<Resume> listStorage = new ArrayList<>();

    @Override
    protected void doSave(Resume r) {
        listStorage.add(r);
    }

    @Override
    protected void doDelete(Resume r) {
        listStorage.remove(r);
    }

    @Override
    protected void doUpdate(Resume r) {
        listStorage.set(getIndex(r.getUuid()), r);
    }

    @Override
    protected Resume doGet(Resume r) {
        return listStorage.get(getIndex(r.getUuid()));
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[size()]);
    }


    protected int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        if (listStorage.contains(resume)) {
            return listStorage.indexOf(resume);
        } else {
            return -1;
        }
    }
}
