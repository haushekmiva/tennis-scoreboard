package com.haushekmiva.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ResponseUtils {

    private ResponseUtils() {}

    public static void forwardUser(HttpServletRequest request, HttpServletResponse response, String forwardPageName)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/" + forwardPageName);
        dispatcher.forward(request, response);
    }

}
