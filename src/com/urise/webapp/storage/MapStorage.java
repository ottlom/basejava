package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    static Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> getKey : mapStorage.entrySet()) {
            if (getKey.getKey().equals(uuid)) {
                return mapStorage.get(getKey.getKey());
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(searchKey.toString());
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        mapStorage.replace(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapStorage.get(searchKey.toString());
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[0]);
    }
}
