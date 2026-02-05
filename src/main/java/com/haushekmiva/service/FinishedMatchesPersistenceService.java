package com.haushekmiva.service;

import com.haushekmiva.dao.MatchHibernateDao;
import com.haushekmiva.dao.PlayerHibernateDao;
import com.haushekmiva.model.Match;
import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.model.Player;
import org.hibernate.SessionFactory;

import java.util.Objects;

public class FinishedMatchesPersistenceService {

    private final PlayerHibernateDao playerHibernateDao;
    private final MatchHibernateDao matchHibernateDao;

    public FinishedMatchesPersistenceService(SessionFactory sessionFactory) {
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
}
