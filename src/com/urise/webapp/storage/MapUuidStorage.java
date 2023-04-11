package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    static Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String uuid) {
        return mapStorage.containsKey(uuid);
    }

    @Override
    protected void doSave(String uuid, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(String uuid) {
        mapStorage.remove(uuid);
    }

    @Override
    protected void doUpdate(String uuid, Resume resume) {
        mapStorage.put(uuid, resume);
    }

    @Override
    protected Resume doGet(String uuid) {
        return mapStorage.get(uuid);
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
    protected List<Resume> doCopyAll() {
        List<Resume> returnAllResume = new ArrayList<>();
        returnAllResume.addAll(mapStorage.values());
        return returnAllResume;
    }
}
