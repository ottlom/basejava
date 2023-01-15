package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    static List<Resume> listStorage = new ArrayList<>();

    @Override
    protected void doSave(Object searchKey) {
        listStorage.add((Resume) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        listStorage.remove(searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey) {
        Resume resume = (Resume) searchKey;
        listStorage.set(getSearchKey(resume.getUuid()), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        Resume resume = (Resume) searchKey;
        return listStorage.get(getSearchKey((resume.getUuid())));
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

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
