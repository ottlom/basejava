package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void save(Resume r);

    void delete(String uuid);

    void update(Resume r);

    void clear();

    int size();

    Resume get(String uuid);

    List<Resume> getAllSorted();
}