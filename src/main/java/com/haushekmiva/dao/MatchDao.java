package com.haushekmiva.dao;

import com.haushekmiva.model.Match;

import java.util.List;

public interface MatchDao {
    Long getMatchCount();

    Long getMatchCountByPlayerName(String playerName);

    List<Match> fetchMatchesSubset(int offset, int count);

    List<Match> fetchMatchesSubsetByPlayerName(int offset, int count, String playerName);

    Long save(Match entity);
}
