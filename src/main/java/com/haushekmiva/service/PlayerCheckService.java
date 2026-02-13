package com.haushekmiva.service;

import com.haushekmiva.dto.MatchParticipantIds;

public interface PlayerCheckService {
    MatchParticipantIds getPlayerIds(String firstPlayerName, String secondPlayerName);

    Long getOrCreateId(String playerName);
}
