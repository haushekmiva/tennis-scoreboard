package com.haushekmiva.controller;

import com.haushekmiva.dto.FinishedMatchSearchDto;
import com.haushekmiva.dto.factory.FinishedMatchSearchDtoFactory;
import com.haushekmiva.exceptions.InvalidParameterValueException;
import com.haushekmiva.model.Match;
import com.haushekmiva.service.FinishedMatchService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.haushekmiva.utils.ResponseUtils.forwardUser;
import static com.haushekmiva.utils.ResponseUtils.redirectUser;


@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 5;
    public static final int PAGE_NUMBER_LIMIT = Integer.MAX_VALUE;
    private FinishedMatchService finishedMatchService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        this.finishedMatchService = (FinishedMatchService) context.getAttribute(
                "finishedMatchService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String playerName = request.getParameter("filter_by_player_name");
        String pageNumberRaw = request.getParameter("page");
        int pageNumber = parsePageNumber(pageNumberRaw);

        if (pageNumber < 0) {
            redirectToPage(response, playerName, 1);
            return;
        }

        List<Match> matches;
        int pageCount;

        if (playerName == null) {
            matches = finishedMatchService.getPaginatedMatches(PAGE_SIZE, pageNumber);
            pageCount = (finishedMatchService.getFinishedMatchCount() + PAGE_SIZE - 1) / PAGE_SIZE;
        } else {
            matches = finishedMatchService.getPaginatedMatchesByPlayerName(PAGE_SIZE, pageNumber, playerName);
            pageCount = (finishedMatchService.getFinishedMatchCountByPlayer(playerName) + PAGE_SIZE - 1) / PAGE_SIZE;
        }

        if (matches.isEmpty() && pageNumber != 1) {
            redirectToPage(response, playerName, 1);
            return;
        }

        if (pageNumber > pageCount && !matches.isEmpty()) {
            redirectToPage(response, playerName, pageCount);
            return;
        }

        FinishedMatchSearchDto finishedMatchSearchDto = FinishedMatchSearchDtoFactory.buildFinishedMatchSearchDto(
                pageNumber,
                pageCount,
                playerName,
                matches
        );

        request.setAttribute("finishedMatchSearchDto", finishedMatchSearchDto);
        forwardUser(request, response, "matches.jsp");
    }

    private int parsePageNumber(String pageNumberRaw) {
        if (pageNumberRaw != null) {
            try {
                return Integer.parseInt(pageNumberRaw);
            } catch (NumberFormatException e) {
                throw new InvalidParameterValueException("Invalid format for the page parameter.");
            }
        }

        return DEFAULT_PAGE_NUMBER;
    }

    private void redirectToPage(HttpServletResponse response, String playerName, int redirectPageNumber) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("page", String.valueOf(redirectPageNumber));
        if (playerName != null && !playerName.isBlank()) {
            parameters.put("filter_by_player_name", playerName);
        }
        redirectUser(response, "matches", parameters);
    }
}
