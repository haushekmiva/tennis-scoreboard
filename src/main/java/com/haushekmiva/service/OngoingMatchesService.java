package com.haushekmiva.service;

import com.haushekmiva.dto.MatchInformation;
import com.haushekmiva.dto.MatchParticipantIds;
import com.haushekmiva.model.OngoingMatchScore;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<UUID, OngoingMatchScore> matches = new ConcurrentHashMap<>();

    public Optional<OngoingMatchScore> getMatch(UUID matchId) {
        return Optional.ofNullable(matches.get(matchId));
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

    public void saveMatch(UUID matchId, OngoingMatchScore match) {
        matches.put(matchId, match);
    }
}
