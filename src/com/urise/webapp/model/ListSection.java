package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private  ArrayList<String> list;

    public ListSection(){}

    public ListSection(ArrayList<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("\n" + (i + 1) + " - " + list.get(i));
            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
