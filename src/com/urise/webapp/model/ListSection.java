package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private ArrayList<String> list;

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
}
