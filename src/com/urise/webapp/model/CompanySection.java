package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private final List<Company> companies;

    public List<Company> getCompanies() {
        return companies;
    }

    public CompanySection(ArrayList<Company> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < companies.size(); i++) {
            sb.append("\n" + (i + 1) + " - " + companies.get(i));
            if (i != companies.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
