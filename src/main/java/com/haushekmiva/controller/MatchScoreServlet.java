package com.haushekmiva.controller;

import com.haushekmiva.dto.FinishedMatchDto;
import com.haushekmiva.dto.MoveResult;
import com.haushekmiva.dto.OngoingMatchScoreDto;
import com.haushekmiva.exceptions.InvalidParameterValueException;
import com.haushekmiva.mapper.FinishedMatchMapper;
import com.haushekmiva.mapper.MatchMapper;
import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.service.OngoingMatchOrchestrator;
import com.haushekmiva.service.OngoingMatchRepository;
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

    private OngoingMatchRepository ongoingMatchRepository;
    private OngoingMatchOrchestrator ongoingMatchOrchestrator;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        this.ongoingMatchRepository = (OngoingMatchRepository) context.getAttribute(
                "ongoingMatchRepository");
        this.ongoingMatchOrchestrator = (OngoingMatchOrchestrator)  context.getAttribute("ongoingMatchOrchestrator");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID matchUuid = extractUuid(request);
        OngoingMatchScore ongoingMatchScore = ongoingMatchRepository.getMatch(matchUuid);
        OngoingMatchScoreDto ongoingMatchScoreDto = MatchMapper.INSTANCE.ongoingMatchScoreToDto(ongoingMatchScore);
        request.setAttribute("ongoingMatchScoreDto", ongoingMatchScoreDto);

        forwardUser(request, response, "match-score.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID matchUuid = extractUuid(request);
        Long playerId = extractPlayerId(request);

        MoveResult moveResult = ongoingMatchOrchestrator.processMove(matchUuid, playerId);

        if (moveResult.isMatchFinished()) {
            FinishedMatchDto finishedMatchDto = FinishedMatchMapper.INSTANCE.ongoingMatchScoreToFinishedMatchDto(moveResult.score());
            request.setAttribute("finishedMatchDto", finishedMatchDto);
            forwardUser(request, response, "finished-match-score.jsp");
        } else {
            OngoingMatchScoreDto ongoingMatchScoreDto = MatchMapper.INSTANCE.ongoingMatchScoreToDto(moveResult.score());
            request.setAttribute("ongoingMatchScoreDto", ongoingMatchScoreDto);
            forwardUser(request, response, "match-score.jsp");
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

    private Long extractPlayerId(HttpServletRequest request) {
        try {
            return Long.valueOf(request.getParameter("playerId"));
        } catch (NumberFormatException e) {
            throw new InvalidParameterValueException("Invalid PlayerId parameter format.");
        }
    }

}
