package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private String text;

    public TextSection (String text) {
        Objects.requireNonNull(text, "Text Section must not be null");
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
