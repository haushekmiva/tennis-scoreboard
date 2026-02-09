package com.haushekmiva.dto;

import com.haushekmiva.model.Match;

import java.util.List;

public record FinishedMatchSearchDto(
    int pageCount,
    int currentPage,
    int prevPage,
    int nextPage,
    String playerName,
    List<Match> matches
    )
{}
