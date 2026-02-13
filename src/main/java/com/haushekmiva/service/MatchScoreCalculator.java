package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;

public interface MatchScoreCalculator {
    void doMove(OngoingMatchScore score, Long playerId);
}
