package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resumePersonal = new Resume("1", "Григорий Кислин");
        resumePersonal.getContacts().put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resumePersonal.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resumePersonal.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        resumePersonal.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resumePersonal.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        resumePersonal.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resumePersonal.getContacts().put(ContactType.HOME_PAGE, "http://gkislin.ru/");
        resumePersonal.getContacts().toString();

        for (Map.Entry<ContactType, String> contact : resumePersonal.getContacts().entrySet()) {
            System.out.println(contact.getKey() + " - " + contact.getValue());
        }

        resumePersonal.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям"));
        resumePersonal.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика," +
                " креативность, инициативность. Пурист кода и архитектуры."));

        ArrayList<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                " приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot," +
                " участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");

        ListSection listSection = new ListSection(achievements);
        resumePersonal.getSections().put(SectionType.ACHIEVEMENT, listSection);

        ArrayList<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");

        listSection = new ListSection(qualification);
        resumePersonal.getSections().put(SectionType.QUALIFICATIONS, listSection);

        Company company = new Company("Alcatel", "http://www.alcatel.ru/");
        Company.Period period = new Company.Period(LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                "Инженер по аппаратному и программному тестированию");
        HashMap<Company, ArrayList<Company.Period>> companyAndPeriods = new HashMap<>();
        CompanySection companySection = new CompanySection(companyAndPeriods);
        resumePersonal.getSections().put(SectionType.EXPERIENCE, companySection);
        companySection.addCompany(company, period);

        company = new Company("Siemens AG", "https://www.siemens.com/ru/ru/home.html/");
        period = new Company.Period(LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1),
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                "Разработчик ПО");
        companySection.addCompany(company, period);

        company = new Company("Siemens AG", "https://www.siemens.com/ru/ru/home.html/");
        period = new Company.Period(LocalDate.of(2009, 2, 1), LocalDate.of(2010, 8, 1),
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                "Разработчик ПО");
        companySection.addCompany(company, period);

        for (Map.Entry<SectionType, AbstractSection> section : resumePersonal.getSections().entrySet()) {
            System.out.println(section.getKey() + ":" + section.getValue());
        }
    }

    public static Resume createResume(String uuid, String name) {
        Resume resume = new Resume(uuid, name);
        resume.getContacts().put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.getContacts().put(ContactType.HOME_PAGE, "http://gkislin.ru/");
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям"));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика," +
                " креативность, инициативность. Пурист кода и архитектуры."));
        ArrayList<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                " приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot," +
                " участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        ListSection listSection = new ListSection(achievements);
        resume.getSections().put(SectionType.ACHIEVEMENT, listSection);
        ArrayList<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.getSections().put(SectionType.QUALIFICATIONS, listSection);
        Company company = new Company("Alcatel", "http://www.alcatel.ru/");
        Company.Period period = new Company.Period(LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                "Инженер по аппаратному и программному тестированию");
        HashMap<Company, ArrayList<Company.Period>> companyAndPeriods = new HashMap<>();
        CompanySection companySection = new CompanySection(companyAndPeriods);
        resume.getSections().put(SectionType.EXPERIENCE, companySection);
        companySection.addCompany(company, period);
        return resume;
    }
}
