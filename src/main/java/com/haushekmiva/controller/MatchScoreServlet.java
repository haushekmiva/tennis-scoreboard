package com.haushekmiva.controller;

import com.haushekmiva.dto.FinishedMatchDto;
import com.haushekmiva.dto.OngoingMatchScoreDto;
import com.haushekmiva.exceptions.InvalidParameterValueException;
import com.haushekmiva.mapper.FinishedMatchMapper;
import com.haushekmiva.mapper.MatchMapper;
import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.service.FinishedMatchesPersistenceService;
import com.haushekmiva.service.MatchScoreCalculationService;
import com.haushekmiva.service.OngoingMatchesService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static com.haushekmiva.validation.RequestValidation.checkRequestParameterEmpty;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        OngoingMatchesService ongoingMatchesService = (OngoingMatchesService) context.getAttribute("ongoingMatchesService");

        UUID matchUuid = extractUuid(request);
        OngoingMatchScore ongoingMatchScore = ongoingMatchesService.getMatch(matchUuid);

        OngoingMatchScoreDto ongoingMatchScoreDto = MatchMapper.INSTANCE.ongoingMatchScoreToDto(ongoingMatchScore);
        request.setAttribute("ongoingMatchScoreDto", ongoingMatchScoreDto);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/match-score.jsp");
        dispatcher.forward(request, response);
    }

    // post method
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        OngoingMatchesService ongoingMatchesService = (OngoingMatchesService) context.getAttribute(
                "ongoingMatchesService");
        MatchScoreCalculationService matchScoreCalculationService = (MatchScoreCalculationService) context.getAttribute(
                "matchScoreCalculationService");
        FinishedMatchesPersistenceService finishedMatchesPersistenceService =
                (FinishedMatchesPersistenceService) context.getAttribute("finishedMatchesPersistenceService");

        UUID matchUuid = extractUuid(request);
        Long playerId = Long.valueOf(request.getParameter("playerId")); // добавить валидацию
        OngoingMatchScore ongoingMatchScore = ongoingMatchesService.getMatch(matchUuid);
        matchScoreCalculationService.doMove(ongoingMatchScore, playerId);

        if (!ongoingMatchScore.isMatchFinished()) {
            OngoingMatchScoreDto ongoingMatchScoreDto = MatchMapper.INSTANCE.ongoingMatchScoreToDto(ongoingMatchScore);
            request.setAttribute("ongoingMatchScoreDto", ongoingMatchScoreDto);
        } else {
            ongoingMatchesService.removeMatch(matchUuid);

            finishedMatchesPersistenceService.saveFinishedMatch(ongoingMatchScore);
            FinishedMatchDto finishedMatchDto = FinishedMatchMapper.INSTANCE.ongoingMatchScoreToFinishedMatchDto(ongoingMatchScore);
            request.setAttribute("finishedMatchDto", finishedMatchDto);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/finished-match-score.jsp");
            dispatcher.forward(request, response);
            return;
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/match-score.jsp");
        dispatcher.forward(request, response);
    }

    private UUID extractUuid(HttpServletRequest request) {
        String matchUUIDRaw = request.getParameter("uuid");
        checkRequestParameterEmpty(matchUUIDRaw, "uuid");
        try {
            return UUID.fromString(matchUUIDRaw);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterValueException("Invalid UUID parameter format.");
        }
    }

}
