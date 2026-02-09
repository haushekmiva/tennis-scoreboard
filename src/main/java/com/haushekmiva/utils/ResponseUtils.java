package com.haushekmiva.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ResponseUtils {

    private ResponseUtils() {}

    public static void forwardUser(HttpServletRequest request, HttpServletResponse response, String forwardPageName)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/" + forwardPageName);
        dispatcher.forward(request, response);
    }

    public static void redirectUser(HttpServletResponse response, String endpoint, Map<String, String> parameters) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(endpoint);
        if (!parameters.isEmpty()) {
            boolean isFirstElement = true;
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                if (isFirstElement) {
                    stringBuilder.append("?");
                    isFirstElement = false;
                } else stringBuilder.append("&");
                stringBuilder.append(parameter.getKey()).append("=").append(parameter.getValue());
            }
        }
        response.sendRedirect(stringBuilder.toString());

    }

}
