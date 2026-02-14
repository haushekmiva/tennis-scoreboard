package com.haushekmiva.service;

import com.haushekmiva.dto.MatchParticipantIds;

public interface PlayerResolver {
    MatchParticipantIds getPlayerIds(String firstPlayerName, String secondPlayerName);
}
