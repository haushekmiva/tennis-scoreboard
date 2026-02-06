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
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static com.haushekmiva.utils.ResponseUtils.forwardUser;
import static com.haushekmiva.validation.RequestValidation.checkRequestParameterEmpty;


@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        this.ongoingMatchesService = (OngoingMatchesService) context.getAttribute(
                "ongoingMatchesService");
        this.matchScoreCalculationService = (MatchScoreCalculationService) context.getAttribute(
                "matchScoreCalculationService");
        this.finishedMatchesPersistenceService =
                (FinishedMatchesPersistenceService) context.getAttribute("finishedMatchesPersistenceService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID matchUuid = extractUuid(request);
        OngoingMatchScore ongoingMatchScore = ongoingMatchesService.getMatch(matchUuid);

        OngoingMatchScoreDto ongoingMatchScoreDto = MatchMapper.INSTANCE.ongoingMatchScoreToDto(ongoingMatchScore);
        request.setAttribute("ongoingMatchScoreDto", ongoingMatchScoreDto);

        forwardUser(request, response, "match-score.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID matchUuid = extractUuid(request);
        Long playerId = Long.valueOf(request.getParameter("playerId")); // добавить валидацию
        OngoingMatchScore ongoingMatchScore = ongoingMatchesService.getMatch(matchUuid);

        matchScoreCalculationService.doMove(ongoingMatchScore, playerId);

        if (!ongoingMatchScore.isMatchFinished()) {
            OngoingMatchScoreDto ongoingMatchScoreDto = MatchMapper.INSTANCE.ongoingMatchScoreToDto(ongoingMatchScore);
            request.setAttribute("ongoingMatchScoreDto", ongoingMatchScoreDto);

            forwardUser(request, response, "match-score.jsp");
        } else {
            ongoingMatchesService.removeMatch(matchUuid);
            finishedMatchesPersistenceService.saveFinishedMatch(ongoingMatchScore);
            FinishedMatchDto finishedMatchDto = FinishedMatchMapper.INSTANCE.ongoingMatchScoreToFinishedMatchDto(ongoingMatchScore);
            request.setAttribute("finishedMatchDto", finishedMatchDto);

            forwardUser(request, response, "finished-match-score.jsp");
        }
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
