package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public ListSection(ArrayList<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
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

        return list.equals(that.list);

    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
