package com.urise.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface CollectionWriter<T> {
    void acceptElement(T t) throws IOException;
}
