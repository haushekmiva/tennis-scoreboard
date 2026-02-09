package com.haushekmiva.service;

import com.haushekmiva.dao.MatchHibernateDao;
import com.haushekmiva.dao.PlayerHibernateDao;
import com.haushekmiva.model.Match;
import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.model.Player;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;

public class FinishedMatchService {

    private final PlayerHibernateDao playerHibernateDao;
    private final MatchHibernateDao matchHibernateDao;

    public FinishedMatchService(SessionFactory sessionFactory) {
        this.playerHibernateDao = new PlayerHibernateDao(sessionFactory);
        this.matchHibernateDao = new MatchHibernateDao(sessionFactory);
    }

    public void saveFinishedMatch(OngoingMatchScore score) {
        Long firstPlayerId = score.getFirstPlayerId();
        Long secondPlayerId = score.getSecondPlayerId();
        Long winnerId = score.getWinnerId();

        Player firstPlayer = playerHibernateDao.getReferenceById(firstPlayerId);
        Player secondPlayer = playerHibernateDao.getReferenceById(secondPlayerId);
        Player winner = Objects.equals(winnerId, firstPlayerId)
                ? firstPlayer
                : secondPlayer;

        matchHibernateDao.save(new Match(winner, secondPlayer, firstPlayer));
    }

    public List<Match> getPaginatedMatches(int pageSize, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return matchHibernateDao.fetchMatchesSubset(offset, pageSize);
    }

    public List<Match> getPaginatedMatchesByPlayerName(int pageSize, int pageNumber, String playerName) {
        int offset = (pageNumber - 1) * pageSize;
        return matchHibernateDao.fetchMatchesSubsetByPlayerName(offset, pageSize, playerName);
    }

    public int getFinishedMatchCount() {
        return matchHibernateDao.getMatchCount().intValue();
    }

    public int getFinishedMatchCountByPlayer(String playerName) {
        return matchHibernateDao.getMatchCountByPlayerName(playerName).intValue();
    }
}
