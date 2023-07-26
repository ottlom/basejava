package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ResumeTestData {
    public static void main(String[] args) {
    }

    public static Resume createResume(String uuid, String name) {
        Resume resume = new Resume(uuid, name);
        resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContact(ContactType.HOME_PAGE, "http://gkislin.ru/");

        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика," +
                " креативность, инициативность. Пурист кода и архитектуры."));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям"));

        ArrayList<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                " приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot," +
                " участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("new list");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievements));

        ArrayList<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("new list");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualification));

        Company company = new Company("Alcatel", "http://www.alcatel.ru/");
        Company.Period period = new Company.Period(LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                "Инженер по аппаратному и программному тестированию");
        company.getCompanyPeriods().add(period);
        CompanySection companySection = new CompanySection();
        companySection.getCompanies().add(company);
        resume.addSection(SectionType.EXPERIENCE,companySection);

        company = new Company("Alcatel", "http://www.alcatel.ru/");
        period = new Company.Period(LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                "Инженер по аппаратному и программному тестированию");
        company.getCompanyPeriods().add(period);
        companySection = new CompanySection();
        companySection.getCompanies().add(company);
        resume.addSection(SectionType.EDUCATION,companySection);
        return resume;
    }
}
