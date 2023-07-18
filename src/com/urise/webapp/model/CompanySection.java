package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Company> companies = new ArrayList<>();

    public CompanySection() {}

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Companies: ");
        for (Company company : companies) {
            sb.append(company.toString()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length() - 1);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
