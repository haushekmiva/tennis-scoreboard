package com.haushekmiva.service;

import com.haushekmiva.dto.MatchInformation;
import com.haushekmiva.exceptions.ResourceNotFoundException;
import com.haushekmiva.model.OngoingMatchScore;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<UUID, OngoingMatchScore> matches = new ConcurrentHashMap<>();

    public OngoingMatchScore getMatch(UUID matchId) {
        OngoingMatchScore match = matches.get(matchId);
        if (match != null) {
            return match;
        }

        throw new ResourceNotFoundException(String.format("Match with UUID %s not found.", matchId));
    }

    public UUID createNewMatch(MatchInformation matchInformation) {
        UUID matchUUID = UUID.randomUUID();
        OngoingMatchScore match = new OngoingMatchScore(
                matchInformation.firstPlayerId(),
                matchInformation.firstPlayerName(),
                matchInformation.secondPlayerId(),
                matchInformation.secondPlayerName()
        );
        matches.put(matchUUID, match);
        return matchUUID;
    }

    public void removeMatch(UUID matchId) {
        matches.remove(matchId);
    }
}
