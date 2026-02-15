package com.haushekmiva.controller;

import com.haushekmiva.dao.MatchDao;
import com.haushekmiva.dao.MatchHibernateDao;
import com.haushekmiva.dao.PlayerDao;
import com.haushekmiva.dao.PlayerHibernateDao;
import com.haushekmiva.service.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        PlayerDao playerDao = new PlayerHibernateDao(sessionFactory);
        MatchDao matchDao = new MatchHibernateDao(sessionFactory);

        OngoingMatchRepository ongoingMatchRepository = new InMemoryOngoingMatchRepository();
        PlayerResolver playerResolver = new PlayerResolverImpl(playerDao);
        FinishedMatchPersistence finishedMatchPersistence = new FinishedMatchPersistenceImpl(playerDao, matchDao);
        MatchScoreCalculator matchScoreCalculator = new MatchScoreCalculatorImpl();
        OngoingMatchOrchestrator ongoingMatchOrchestrator = new OngoingMatchOrchestratorImpl(
                ongoingMatchRepository,
                matchScoreCalculator,
                finishedMatchPersistence
        );

        context.setAttribute("ongoingMatchRepository", ongoingMatchRepository);
        context.setAttribute("playerResolver", playerResolver);
        context.setAttribute("finishedMatchPersistence", finishedMatchPersistence);
        context.setAttribute("matchScoreCalculator", matchScoreCalculator);
        context.setAttribute("ongoingMatchOrchestrator", ongoingMatchOrchestrator);


        log.info("Application started.");
    }

}