package com.urise.webapp.model;

import java.util.*;

public class CompanySection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private HashMap<Company, ArrayList<Company.Period>> companies;

    public CompanySection() {}

    public CompanySection(HashMap<Company, ArrayList<Company.Period>> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    public HashMap<Company, ArrayList<Company.Period>> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Company, ArrayList<Company.Period>> entry : companies.entrySet()) {
            Company company = entry.getKey();
            sb.append("\n" + company.getName()).append(", ").append(company.getWebsite()).append(":").append("\n");
            List<Company.Period> periods = entry.getValue();
            for (int i = 0; i < periods.size(); i++) {
                sb.append((i + 1) + ".").append(periods.get(i));
                if (i < periods.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companies);
    }

    public void addCompany(Company company, Company.Period period) {
        if (getCompanies().containsKey(company)) {
            List<Company.Period> listPeriods = getCompanies().get(company);
            listPeriods.add(period);
        } else {
            ArrayList<Company.Period> listPeriods = new ArrayList<>();
            listPeriods.add(period);
            getCompanies().put(company, listPeriods);
        }
    }
}
