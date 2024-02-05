package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private final Config config = Config.get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", config.getStorage().getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                config.getStorage().delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = config.getStorage().get(uuid);
                break;
            case "create":
                request.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(request, response);
                return;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume r;
        final boolean isCreate = (uuid == null || uuid.length() == 0);

        if (fullName == null || fullName.trim().isEmpty()) {
            response.sendRedirect("resume");
        } else {
            if (isCreate) {
                r = new Resume(fullName);

                doSavePortfolioData(request, r);

                config.getStorage().save(r);
                response.sendRedirect("resume");
            } else {
                r = config.getStorage().get(uuid);
                r.setFullName(fullName);

                doSavePortfolioData(request, r);
                config.getStorage().update(r);
                response.sendRedirect("resume");
            }
        }
    }

    private void doSavePortfolioData(HttpServletRequest request, Resume r) {
        for (ContactType contact : ContactType.values()) {
            String value = request.getParameter(contact.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(contact, value);
            } else {
                r.getContacts().put(contact, "");
            }
        }

        for (SectionType section : SectionType.values()) {
            String value = request.getParameter(section.toString());
            AbstractSection typeSection = insertDataInSection(section.name(), value);
            if (value != null && value.trim().length() != 0) {
                r.addSection(section, typeSection);
            } else {
                r.getSections().put(section, typeSection);
            }
        }
    }

    private AbstractSection insertDataInSection(String typeSection, String value) {
        AbstractSection section = null;
        switch (typeSection) {
            case "OBJECTIVE":
            case "PERSONAL":
                section = new TextSection(value);
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                ArrayList<String> list = new ArrayList<>();
                Arrays.stream(value.split("\n"))
                        .forEach(s -> {
                            if (s != null && s.trim().length() != 0) {
                                list.add(s);
                            }
                        });
                section = new ListSection(list);
                break;
            case "EXPERIENCE":
            case "EDUCATION":
                break;
        }
        return section;
    }
}
