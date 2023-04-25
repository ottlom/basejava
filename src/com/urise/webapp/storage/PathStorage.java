package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.exception.StorageException;
import com.urise.webapp.storage.serialization.Serializations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private Serializations serializations;

    protected PathStorage(String dir, Serializations serializations) {
        directory = Paths.get(dir);
        this.serializations = serializations;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            serializations.doWrite(r, new FileOutputStream(path.toString()));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return path.toFile().exists();
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
            serializations.doWrite(r, new FileOutputStream(path.toString()));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializations.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            if (!Files.deleteIfExists(path)) {
                throw new StorageException("Path delete error", path.toString());
            }
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try (Stream<Path> stream = Files.list(directory)) {
            stream.map(this::doGet).forEach(list::add);
        } catch (IOException e) {
            throw new StorageException("IO error", directory.toString(), e);
        }
        return list;
    }
}