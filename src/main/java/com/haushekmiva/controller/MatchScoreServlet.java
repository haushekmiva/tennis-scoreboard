package com.haushekmiva.controller;

import com.haushekmiva.dto.OngoingMatchScoreDto;
import com.haushekmiva.exceptions.InvalidParameterValueException;
import com.haushekmiva.mapper.MatchMapper;
import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.service.OngoingMatchesService;
import com.haushekmiva.validation.ValidationErrorMessages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static com.haushekmiva.validation.InputValidation.checkFieldEmpty;
import static com.haushekmiva.validation.RequestValidation.checkRequestParameterEmpty;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        OngoingMatchesService ongoingMatchesService = (OngoingMatchesService) context.getAttribute("ongoingMatchesService");

        String matchUUIDRaw = request.getParameter("uuid");
        checkRequestParameterEmpty(matchUUIDRaw, "uuid");

        // надо будет как-нибудь вынести
        UUID matchUUID;
        try {
            matchUUID = UUID.fromString(matchUUIDRaw);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterValueException("Invalid UUID parameter format.");
        }

        OngoingMatchScore ongoingMatchScore = ongoingMatchesService.getMatch(matchUUID);

        // хз работает ли, надо будет проверить
        OngoingMatchScoreDto ongoingMatchScoreDto = MatchMapper.INSTANCE.ongoingMatchScoreToDto(ongoingMatchScore);

        request.setAttribute("ongoingMatchScoreDto", ongoingMatchScoreDto);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/match-score.jsp");
        dispatcher.forward(request, response);
    }
}
