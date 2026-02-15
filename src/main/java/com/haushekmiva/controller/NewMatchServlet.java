package com.haushekmiva.controller;

import com.haushekmiva.dto.MatchInformation;
import com.haushekmiva.dto.MatchParticipantIds;
import com.haushekmiva.service.OngoingMatchRepository;
import com.haushekmiva.service.PlayerResolver;
import com.haushekmiva.validation.ValidationErrorMessages;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.haushekmiva.utils.ResponseUtils.forwardUser;
import static com.haushekmiva.validation.InputValidation.checkFieldEmpty;
import static com.haushekmiva.validation.InputValidation.checkInputLength;
import static com.haushekmiva.validation.ObjectLevelValidation.checkFieldsEqual;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(NewMatchServlet.class);

    private OngoingMatchRepository ongoingMatchRepository;
    private PlayerResolver playerResolver;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        this.ongoingMatchRepository = (OngoingMatchRepository) context.getAttribute(
                "ongoingMatchRepository");
        this.playerResolver = (PlayerResolver) context.getAttribute("playerResolver");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardUser(request, response, "new-match.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String firstPlayerName = request.getParameter("firstPlayerName");
        String secondPlayerName = request.getParameter("secondPlayerName");

        ValidationErrorMessages errorMessages = new ValidationErrorMessages();

        checkFieldEmpty(errorMessages, firstPlayerName, "Field player one cannot be empty.");
        checkFieldEmpty(errorMessages, secondPlayerName, "Field player two cannot be empty.");
        checkInputLength(errorMessages, firstPlayerName, 16, "Player name must be 16 characters or fewer.");
        checkInputLength(errorMessages, secondPlayerName, 16, "Player name must be 16 characters or fewer.");

        checkFieldsEqual(
                        errorMessages,
                        firstPlayerName,
                        secondPlayerName,
                        "Player names must be different."
        );

        if (errorMessages.hasErrors()) {
            Optional<String> firstErrorMessage = errorMessages.getFirstErrorMessage();
            firstErrorMessage.ifPresent(string -> request.setAttribute("errorMessage", string));
            forwardUser(request, response, "new-match.jsp");
            return;
        }

        MatchParticipantIds matchParticipantIds = playerResolver.getPlayerIds(firstPlayerName, secondPlayerName);

        Long firstPlayerId = matchParticipantIds.firstPlayerId();
        Long secondPlayerId = matchParticipantIds.secondPlayerId();

        UUID matchUUID = ongoingMatchRepository.createNewMatch(
                new MatchInformation(
                        firstPlayerId,
                        firstPlayerName,
                        secondPlayerId,
                        secondPlayerName
                )
        );

        log.info("New matched created (UUID: {})", matchUUID.toString());
        response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchUUID.toString());
    }

}
