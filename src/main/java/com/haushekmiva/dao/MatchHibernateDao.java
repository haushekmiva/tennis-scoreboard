package com.haushekmiva.dao;

import com.haushekmiva.exceptions.DataAccessException;
import com.haushekmiva.model.Match;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;

import static org.hibernate.Hibernate.list;

public class MatchHibernateDao extends HibernateDao<Match, Long> {

    public MatchHibernateDao(SessionFactory sessionFactory) {
        super(Match.class, sessionFactory);
    }

    public Long getMatchCount() {
        try (Session session = super.getSessionFactory().openSession()) {
            return session.createQuery("select count(e) from Match e", Long.class)
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new DataAccessException("Ошибка при получении кол-ва записей из бд.", e);
        }
    }

    public Long getMatchCountByPlayerName(String playerName) {
        try (Session session = super.getSessionFactory().openSession()) {
            return session.createQuery("select count(e) from Match e where e.winner.name like :name", Long.class)
                    .setParameter("name", "%" + playerName + "%")
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new DataAccessException("Ошибка при получении кол-ва записей из бд.", e);
        }
    }

    public List<Match> fetchMatchesSubset(int offset, int count) {
        try (Session session = super.getSessionFactory().openSession()) {
            return session.createQuery("from Match", Match.class)
                    .setFirstResult(offset)
                    .setMaxResults(count)
                    .list();
        }
        catch (HibernateException e) {
            throw new DataAccessException("Ошибка при получении данных из бд.");
        }
    }

    public List<Match> fetchMatchesSubsetByPlayerName(int offset, int count, String playerName) {
        try (Session session = super.getSessionFactory().openSession()) {
            return session.createQuery("from Match e where e.winner.name like :name", Match.class)
                    .setParameter("name", "%" + playerName + "%")
                    .setFirstResult(offset)
                    .setMaxResults(count)
                    .list();
        }
        catch (HibernateException e) {
            throw new DataAccessException("Ошибка при получении данных из бд.");
        }
    }

}
