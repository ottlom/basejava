package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private final Config config = Config.get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-16");
        response.setCharacterEncoding("UTF-16");
        for (Resume resume : config.getStorage().getAllSorted()) {
            response.getWriter().print("uuid: " + resume.getUuid() + ", name: " + resume.getFullName() + "\n");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
