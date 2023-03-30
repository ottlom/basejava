package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.ArrayList;
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

        for (Map.Entry<ContactType,String> contact : resumePersonal.getContacts().entrySet()) {
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
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С," +
                " Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery." +
                " Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC," +
                " GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish)."
                + " Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios." +
                " Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России" +
                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        ListSection listSection = new ListSection(achievements);
        resumePersonal.getSections().put(SectionType.ACHIEVEMENT, listSection);

        ArrayList<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot)," +
                " JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("Python: Django.");
        qualification.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM," +
                " XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualification.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualification.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualification.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualification.add("Родной русский, английский \"upper intermediate\"");

        listSection = new ListSection(qualification);
        resumePersonal.getSections().put(SectionType.QUALIFICATIONS, listSection);

        ArrayList<Company> arrayCompany = new ArrayList<>();
        Company.Period period = new Company.Period("09/1997","01/2005",
                "тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                "Инженер по аппаратному и программному тестированию");
        Company company = new Company("Alcatel","http://www.alcatel.ru/",period);
        arrayCompany.add(company);

        period = new Company.Period("01/2005","02/2007",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                "Разработчик ПО");
        company = new Company("Siemens AG","https://www.siemens.com/ru/ru/home.html/",period);
        arrayCompany.add(company);

        period = new Company.Period("03/2007","06/2008",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
                "Разработчик ПО");
        company = new Company("Enkata","http://enkata.com/",period);
        arrayCompany.add(company);

        period = new Company.Period("06/2008","12/2010",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2)." +
                        " Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                "Ведущий специалист");
        company = new Company("Yota","https://www.yota.ru/",period);
        arrayCompany.add(company);

        period = new Company.Period("12/2010","04/2012",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle)." +
                        " Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга." +
                        " JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                "Ведущий программист");
        company = new Company("Luxoft","http://www.luxoft.ru/",period);
        arrayCompany.add(company);

        period = new Company.Period("04/2012","10/2014",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway)," +
                        " конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                        "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                        "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                        "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                "Java архитектор");
        company = new Company("RIT Center",null ,period);
        arrayCompany.add(company);

        period = new Company.Period("10/2014","01/2016",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                        " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                "Старший разработчик");
        company = new Company("Wrike","https://www.wrike.com/",period);
        arrayCompany.add(company);

        period = new Company.Period("10/2013","now",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                "Автор проекта.");
        company = new Company("Java Online Projects","http://javaops.ru/",period);
        arrayCompany.add(company);

        CompanySection companySection = new CompanySection(arrayCompany);
        resumePersonal.getSections().put(SectionType.EXPERIENCE,companySection);

        for (Map.Entry<SectionType,AbstractSection> textSection: resumePersonal.getSections().entrySet()) {
            System.out.println(textSection.getKey() + " - " + textSection.getValue());
        }
    }
}
