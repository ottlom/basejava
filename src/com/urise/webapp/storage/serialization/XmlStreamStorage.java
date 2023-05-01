package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamStorage implements Serializations {
    private final XmlParser xmlParser;

    public XmlStreamStorage() {
        xmlParser = new XmlParser(Resume.class, Company.class, CompanySection.class, TextSection.class,
                ListSection.class,Company.Period.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is,StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
