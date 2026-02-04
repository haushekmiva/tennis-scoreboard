package com.haushekmiva.dao;

import com.haushekmiva.model.Match;
import org.hibernate.SessionFactory;

public class MatchHibernateDao extends HibernateDao<Match, Long> {

    public MatchHibernateDao(SessionFactory sessionFactory) {
        super(Match.class, sessionFactory);
    }

}
