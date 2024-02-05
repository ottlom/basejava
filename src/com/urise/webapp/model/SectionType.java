package com.urise.webapp.model;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения") {
        @Override
        protected String toHtml0(String value) {
            return String.join("\n", value);
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        protected String toHtml0(String value) {
            return String.join("\n", value);
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='Опыт работы: " + value + "'>" + value + "</a>";
        }
    },
    EDUCATION("Образование") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='Образование: " + value + "'>" + value + "</a>";
        }
    };

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }
}
