package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    static Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume resume, Resume res) {
        mapStorage.put((res.getUuid()), res);
    }

    @Override
    protected void doDelete(Resume resume) {
        mapStorage.remove(resume.getUuid());
    }

    @Override
    protected void doUpdate(Resume resume, Resume res) {
        mapStorage.put(res.getUuid(), res);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
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
