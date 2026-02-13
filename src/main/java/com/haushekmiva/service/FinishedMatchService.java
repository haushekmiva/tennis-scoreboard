package com.haushekmiva.service;

import com.haushekmiva.dao.MatchDao;
import com.haushekmiva.dao.PlayerDao;
import com.haushekmiva.model.Match;
import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.model.Player;

import java.util.List;
import java.util.Objects;

public class FinishedMatchService {

    private final PlayerDao playerDao;
    private final MatchDao matchDao;

    public FinishedMatchService(PlayerDao playerDao, MatchDao matchDao) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
    }

    public void saveFinishedMatch(OngoingMatchScore score) {
        Long firstPlayerId = score.getFirstPlayerId();
        Long secondPlayerId = score.getSecondPlayerId();
        Long winnerId = score.getWinnerId();

        Player firstPlayer = playerDao.getReferenceById(firstPlayerId);
        Player secondPlayer = playerDao.getReferenceById(secondPlayerId);
        Player winner = Objects.equals(winnerId, firstPlayerId)
                ? firstPlayer
                : secondPlayer;

        matchDao.save(new Match(winner, secondPlayer, firstPlayer));
    }

    public List<Match> getPaginatedMatches(int pageSize, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return matchDao.fetchMatchesSubset(offset, pageSize);
    }

    public List<Match> getPaginatedMatchesByPlayerName(int pageSize, int pageNumber, String playerName) {
        int offset = (pageNumber - 1) * pageSize;
        return matchDao.fetchMatchesSubsetByPlayerName(offset, pageSize, playerName);
    }

    public int getFinishedMatchCount() {
        return matchDao.getMatchCount().intValue();
    }

    public int getFinishedMatchCountByPlayer(String playerName) {
        return matchDao.getMatchCountByPlayerName(playerName).intValue();
    }
}
