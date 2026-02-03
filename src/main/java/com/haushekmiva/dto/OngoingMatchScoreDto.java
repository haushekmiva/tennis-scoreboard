package com.haushekmiva.dto;

public record OngoingMatchScoreDto(
        String firstPlayerName,
        String firstPlayerPoints,
        String firstPlayerGames,
        String firstPlayerSets,
        String secondPlayerName,
        String secondPlayerPoints,
        String secondPlayerGames,
        String secondPlayerSets
) {}
