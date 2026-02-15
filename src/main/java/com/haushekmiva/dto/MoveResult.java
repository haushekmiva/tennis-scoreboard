package com.haushekmiva.dto;

import com.haushekmiva.model.OngoingMatchScore;

public record MoveResult(
        boolean isMatchFinished,
        OngoingMatchScore score
) {}
