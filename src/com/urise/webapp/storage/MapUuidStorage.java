package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    static Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object uuid) {
        return mapStorage.containsKey((String) uuid);
    }

    @Override
    protected void doSave(Object uuid, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object uuid) {
        mapStorage.remove((String) uuid);
    }

    @Override
    protected void doUpdate(Object uuid, Resume resume) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return mapStorage.get((String) uuid);
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
    protected List<Resume> getCopyStorage() {
        List<Resume> returnAllResume = new ArrayList<>();
        returnAllResume.addAll(mapStorage.values());
        return returnAllResume;
    }
}
