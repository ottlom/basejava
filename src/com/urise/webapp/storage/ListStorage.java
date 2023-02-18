package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    static List<Resume> listStorage = new ArrayList<>();

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        listStorage.add(resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        listStorage.remove(listStorage.get((Integer) searchKey));
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        listStorage.set(getSearchKey(resume.getUuid()), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return listStorage.get((Integer) searchKey);
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
    protected List<Resume> getCopyStorage() {
        return new ArrayList<>(listStorage);
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
