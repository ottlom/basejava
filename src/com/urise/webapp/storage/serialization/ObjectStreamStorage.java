package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.exception.StorageException;

import java.io.*;

public class ObjectStreamStorage implements Serializations {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream iis = new ObjectInputStream(is)) {
            return (Resume) iis.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
