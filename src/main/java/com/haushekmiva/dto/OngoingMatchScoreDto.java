package com.haushekmiva.dto;

public record OngoingMatchScoreDto(
        String firstPlayerId,
        String firstPlayerName,
        String firstPlayerPoints,
        String firstPlayerGames,
        String firstPlayerSets,
        String secondPlayerId,
        String secondPlayerName,
        String secondPlayerPoints,
        String secondPlayerGames,
        String secondPlayerSets
) {}
