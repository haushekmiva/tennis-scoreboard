package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<UUID, OngoingMatchScore> playerScores = new ConcurrentHashMap<>();

    public Optional<OngoingMatchScore> getMatch(UUID matchId) {
        return Optional.ofNullable(playerScores.get(matchId));
    }

    public void saveMatch(UUID matchId, OngoingMatchScore match) {
        playerScores.put(matchId, match);
    }


}
