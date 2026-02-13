package com.haushekmiva.service;

import com.haushekmiva.dto.MatchParticipantIds;
import com.haushekmiva.model.Player;

import java.util.Optional;

public interface PlayerCheckService {
    MatchParticipantIds getPlayerIds(String firstPlayerName, String secondPlayerName);

    Long getOrCreateId(String playerName);
}
