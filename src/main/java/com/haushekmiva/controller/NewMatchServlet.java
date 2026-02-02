package com.haushekmiva.controller;

import com.haushekmiva.dto.MatchInformation;
import com.haushekmiva.dto.MatchParticipantIds;
import com.haushekmiva.service.OngoingMatchesService;
import com.haushekmiva.service.PlayerCheckService;
import com.haushekmiva.validation.ValidationErrorMessages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.haushekmiva.validation.FieldValidation.checkFieldEmpty;
import static com.haushekmiva.validation.ObjectLevelValidation.checkFieldsEqual;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/new-match.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        String firstPlayerName = request.getParameter("firstPlayerName");
        String secondPlayerName = request.getParameter("secondPlayerName");

        ValidationErrorMessages errorMessages = new ValidationErrorMessages();

        checkFieldEmpty(errorMessages, firstPlayerName, "Field player one cannot be empty.");
        checkFieldEmpty(errorMessages, secondPlayerName, "Field player two cannot be empty.");

        checkFieldsEqual(
                        errorMessages,
                        firstPlayerName,
                        secondPlayerName,
                        "Player names must be different."
        );

        if (errorMessages.hasErrors()) {
            Optional<String> firstErrorMessage = errorMessages.getFirstErrorMessage();

            firstErrorMessage.ifPresent(string -> request.setAttribute("errorMessage", string));

            request.getRequestDispatcher("/WEB-INF/view/new-match.jsp").forward(request, response);
            return;
        }

        PlayerCheckService playerCheckService = (PlayerCheckService) context.getAttribute("playerCheckService");
        MatchParticipantIds matchParticipantIds = playerCheckService.getPlayerIds(firstPlayerName, secondPlayerName);

        Long firstPlayerId = matchParticipantIds.firstPlayerId();
        Long secondPlayerId = matchParticipantIds.secondPlayerId();

        OngoingMatchesService ongoingMatchesService = (OngoingMatchesService) context.getAttribute("ongoingMatchesService");
        UUID matchUUID = ongoingMatchesService.createNewMatch(
                new MatchInformation(
                        firstPlayerId,
                        firstPlayerName,
                        secondPlayerId,
                        secondPlayerName
                )
        );

        response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchUUID.toString());
    }

}
