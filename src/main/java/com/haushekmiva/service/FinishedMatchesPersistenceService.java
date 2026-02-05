package com.haushekmiva.service;

import com.haushekmiva.dao.MatchHibernateDao;
import com.haushekmiva.dao.PlayerHibernateDao;
import com.haushekmiva.model.Match;
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

    public void finishedMatch(Long firstPlayerId, Long secondPlayerId, Long winnerId) {
        Player firstPlayer = playerHibernateDao.getReferenceById(firstPlayerId);
        Player secondPlayer = playerHibernateDao.getReferenceById(secondPlayerId);
        Player winner;
        if (Objects.equals(winnerId, firstPlayerId)) {
            winner = firstPlayer;
        } else {
            winner = secondPlayer;
        }

        matchHibernateDao.save(new Match(winner, secondPlayer, firstPlayer));

    }

}
