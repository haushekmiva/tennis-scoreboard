package com.haushekmiva.dao;

import com.haushekmiva.model.Player;
import org.hibernate.SessionFactory;

public class PlayerHibernateDao extends HibernateDao<Player, Long>{

    public PlayerHibernateDao(SessionFactory sessionFactory) {
        super(Player.class, sessionFactory);
    }

}
