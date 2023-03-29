package com.urise.webapp.model;

public enum ContactType {
    PHONE_NUMBER("номер телефона"),
    SKYPE("скайп логин"),
    MAIL("почта"),
    LINKEDIN("ссылка на линкед ин"),
    GITHUB("ссылка на гитхаб"),
    STACKOVERFLOW("ссылка на стэк оверфлоу"),
    HOME_PAGE("домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
