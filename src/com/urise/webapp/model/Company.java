package com.urise.webapp.model;

import com.google.gson.annotations.JsonAdapter;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String website;
    private final List<Period> companyPeriods = new ArrayList<>();

    public Company() {
    }

    public Company(String name, String website) {
        Objects.requireNonNull(name, "Name company must not be null");
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getCompanyPeriods() {
        return companyPeriods;
    }

    @Override
    public String toString() {
        return "Company(" + name + ',' + website + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) &&
                Objects.equals(website, company.website) &&
                Objects.equals(companyPeriods, company.companyPeriods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, companyPeriods);
    }

    //Nested class Period
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        @JsonAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        @JsonAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String description;
        private String title;

        public Period() {
        }

        public Period(LocalDate startDate, LocalDate endDate, String description, String title) {
            Objects.requireNonNull(startDate, "start period must not be null");
            Objects.requireNonNull(endDate, "end period must not be null");
            Objects.requireNonNull(description, "description must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
            this.title = title;
        }

        public LocalDate getStartPeriod() {
            return startDate;
        }

        public LocalDate getEndPeriod() {
            return endDate;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "title - " + title + '\n' +
                    "description - " + description + '\n' +
                    "startPeriod - " + startDate + '\n' +
                    "endPeriod - " + endDate + '\n';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return Objects.equals(startDate, period.startDate) &&
                    Objects.equals(endDate, period.endDate) &&
                    Objects.equals(description, period.description) &&
                    Objects.equals(title, period.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, description, title);
        }
    }
}
