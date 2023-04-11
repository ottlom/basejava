package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company {
    private final String name;
    private final String website;

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

    @Override
    public String toString() {
        return "Company(" + name + ',' + website + ')';
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;
        return website != null ? website.equals(company.website) : company.website == null;

    }

    //
    ///
    ////Nested class Period
    public static class Period {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;
        private final String title;

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
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period that = (Period) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!title.equals(that.title)) return false;
            return description != null ? description.equals(that.description) : that.description == null;

        }
    }
}
