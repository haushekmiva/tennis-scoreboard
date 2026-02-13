package com.haushekmiva.service;

import com.haushekmiva.dto.MatchInformation;
import com.haushekmiva.model.OngoingMatchScore;

import java.util.UUID;

public interface OngoingMatchRepository {
    OngoingMatchScore getMatch(UUID matchId);

    UUID createNewMatch(MatchInformation matchInformation);

    void removeMatch(UUID matchId);
}
