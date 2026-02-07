package com.haushekmiva.controller;

import com.haushekmiva.service.FinishedMatchService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {

    private FinishedMatchService finishedMatchService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        this.finishedMatchService = (FinishedMatchService) context.getAttribute(
                "finishedMatchService");
    }
}
