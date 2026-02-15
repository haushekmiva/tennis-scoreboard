package com.haushekmiva.service;

import com.haushekmiva.dto.MoveResult;

import java.util.UUID;

public interface OngoingMatchOrchestrator {
    MoveResult processMove(UUID matchUuid, Long playerId);
}
