package com.urise.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface ElementReader {
    void acceptElement() throws IOException;
}
