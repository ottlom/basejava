package com.urise.webapp.model;

public enum ContactType {
    PHONE_NUMBER("номер телефона")
    ,SKYPE("skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype: " + value + "'>" + value + "</a>";
        }
    }, MAIL("email") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto: " + value + "'>" + value + "</a>";
        }
    }, LINKEDIN("LinkedIn") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='LinkedIn" + value + "'>" + value + "</a>";
        }
    }, GITHUB("GitHub") {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    }, STACKOVERFLOW("Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    }, HOME_PAGE("домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    };

    private String title;

    ContactType(String title) {
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

    @Override
    public String toString() {
        return String.join("\n", title);
    }
}
