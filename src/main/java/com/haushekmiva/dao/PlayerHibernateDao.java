package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import com.haushekmiva.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PlayerHibernateDao extends HibernateDao<Player, Long>{

    public PlayerHibernateDao(SessionFactory sessionFactory) {
        super(Player.class, sessionFactory);
    }

    public Optional<Player> findByName(String name) {
        try (Session session = super.getSessionFactory().openSession()) {

            Query<Player> query = session.createQuery(
                    "FROM players p WHERE p.name = :name", Player.class);
            query.setParameter("email", "example@mail.com");
            List<Player> players = query.list();

            if (players.isEmpty()) {
                return Optional.empty();
            } else return Optional.of(players.get(0));
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

}
