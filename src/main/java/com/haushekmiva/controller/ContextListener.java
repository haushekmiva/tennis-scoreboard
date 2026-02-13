package com.haushekmiva.controller;

import com.haushekmiva.dao.MatchDao;
import com.haushekmiva.dao.MatchHibernateDao;
import com.haushekmiva.dao.PlayerDao;
import com.haushekmiva.dao.PlayerHibernateDao;
import com.haushekmiva.service.FinishedMatchService;
import com.haushekmiva.service.MatchScoreCalculationService;
import com.haushekmiva.service.OngoingMatchesService;
import com.haushekmiva.service.PlayerCheckService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        context.setAttribute("sessionFactory", sessionFactory);

        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        context.setAttribute("ongoingMatchesService", ongoingMatchesService);

        PlayerDao playerDao = new PlayerHibernateDao(sessionFactory);
        MatchDao matchDao = new MatchHibernateDao(sessionFactory);

        PlayerCheckService playerCheckService = new PlayerCheckService(playerDao);
        context.setAttribute("playerCheckService", playerCheckService);

        FinishedMatchService finishedMatchService =
                new FinishedMatchService(playerDao, matchDao);
        context.setAttribute("finishedMatchService", finishedMatchService);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        context.setAttribute("matchScoreCalculationService", matchScoreCalculationService);

        System.out.println("Application started.");
    }

}