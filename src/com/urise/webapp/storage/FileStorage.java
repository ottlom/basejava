package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.exception.StorageException;
import com.urise.webapp.storage.serialization.Serializations;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Serializations serializations;

    protected FileStorage(File directory, Serializations serializations) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializations = serializations;
    }

    @Override
    public void clear() {
        if (isDirectoryNull()) {
            throw new StorageException("file is empty", null);
        } else {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                doDelete(file);
            }
        }

    }

    @Override
    public int size() {
        if (isDirectoryNull()) {
            throw new StorageException("file is empty", null);
        } else return Objects.requireNonNull(directory.listFiles()).length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            serializations.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("write error", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializations.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("file delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        if (isDirectoryNull()) {
            throw new StorageException("file doesn't have any resume", null);
        } else {
            List<Resume> list = new ArrayList<>(Objects.requireNonNull(directory.listFiles()).length);
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                list.add(doGet(file));
            }
            return list;
        }
    }

    private boolean isDirectoryNull() {
        return directory.listFiles() == null;
    }
}