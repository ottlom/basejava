package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{
    static Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Object resume, Resume res) {
        mapStorage.put((res.getUuid()), res);
    }

    @Override
    protected void doDelete(Object resume) {
        mapStorage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected void doUpdate(Object resume, Resume res) {
        mapStorage.put(res.getUuid(), res);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
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
