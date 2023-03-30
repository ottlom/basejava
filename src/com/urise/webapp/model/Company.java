package com.urise.webapp.model;

import java.util.Objects;

public class Company {
    private final String name;
    private final String website;
    private final Period period;

    public Company(String name,String website, Period period) {
        Objects.requireNonNull(name, "Name company must not be null");
        Objects.requireNonNull(period, "Period Section must not be null");
        this.name = name;
        this.website = website;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public Period getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return  getName() +
                ", " + getWebsite() +
                ", " + period.toString();
    }

    //
    ///
    ////Nested class Period
    public static class Period {
        private final String startPeriod;
        private final String endPeriod;
        private final String description;
        private final String title;

        public Period(String startPeriod,String endPeriod,String description,String title) {
            Objects.requireNonNull(startPeriod, "start period must not be null");
            Objects.requireNonNull(endPeriod, "end period must not be null");
            Objects.requireNonNull(description, "description must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startPeriod = startPeriod;
            this.endPeriod = endPeriod;
            this.description = description;
            this.title = title;
        }

        public String getStartPeriod() {
            return startPeriod;
        }

        public String getEndPeriod() {
            return endPeriod;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return  "startPeriod='" + startPeriod + '\'' +
                    ", endPeriod='" + endPeriod + '\'' +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
