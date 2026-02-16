package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import com.haushekmiva.model.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class PlayerHibernateDao extends HibernateDao<Player, Long> implements PlayerDao {

    private static final Logger log = LoggerFactory.getLogger(PlayerHibernateDao.class);


    public PlayerHibernateDao(SessionFactory sessionFactory) {
        super(Player.class, sessionFactory);
    }

    public Optional<Player> findByName(String name) {
        try (Session session = super.getSessionFactory().openSession()) {

            Query<Player> query = session.createQuery(
                    "FROM Player p WHERE p.name = :name", Player.class);
            query.setParameter("name", name);
            List<Player> players = query.list();

            if (players.isEmpty()) {
                return Optional.empty();
            } else return Optional.of(players.get(0));
        } catch (HibernateException e) {
            log.error("Failed to find Player by name: {}", name, e);
            throw new DataAccessException(e);
        }
    }

}
