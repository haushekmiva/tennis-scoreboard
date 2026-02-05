package com.haushekmiva.dto;

import com.haushekmiva.mapper.PlayerNumbers;
import com.haushekmiva.model.SetScores;

import java.util.ArrayList;

public record FinishedMatchDto(
        String firstPlayerName,
        String secondPlayerName,
        PlayerNumbers winnerNumber,
        ArrayList<SetScores> setScores
) {
}
