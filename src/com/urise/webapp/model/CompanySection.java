package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private ArrayList<Company> companies;

    public ArrayList<Company> getCompanies() {
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

    //
    ///
    ////Nested Class Company
    public static class Company {
        private String name;
        private String webSite;
        private Period period;

        public Company(String name,String webSite, Period period) {
            Objects.requireNonNull(name, "Name company must not be null");
            Objects.requireNonNull(period, "Period Section must not be null");
            this.name = name;
            this.webSite = webSite;
            this.period = period;
        }

        public String getName() {
            return name;
        }

        public String getWebSite() {
            if (webSite == null) {
                return "сайта нет";
            }
            return webSite;
        }

        public Period getPeriod() {
            return period;
        }

        @Override
        public String toString() {
            return  getName() +
                    ", " + getWebSite() +
                    ", " + period.toString();
        }

        //
        ///
        ////Nested class Period
        public static class Period {
            private String startPeriod;
            private String endPeriod;
            private String description;
            private String title;

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
}
