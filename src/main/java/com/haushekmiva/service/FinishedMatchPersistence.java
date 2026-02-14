package com.haushekmiva.service;

import com.haushekmiva.model.Match;
import com.haushekmiva.model.OngoingMatchScore;

import java.util.List;

public interface FinishedMatchPersistence {
    void saveFinishedMatch(OngoingMatchScore score);

    List<Match> getPaginatedMatches(int pageSize, int pageNumber);

    List<Match> getPaginatedMatchesByPlayerName(int pageSize, int pageNumber, String playerName);

    int getFinishedMatchCount();

    int getFinishedMatchCountByPlayer(String playerName);
}
