package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<String> list;

    public ListSection(){}

    public ListSection(ArrayList<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public ArrayList<String> getList() {
        return new ArrayList<>(list);
    }

    @Override
    public String toString() {
        return String.join("\n", list);
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
