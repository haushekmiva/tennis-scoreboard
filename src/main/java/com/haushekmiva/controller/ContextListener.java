package com.haushekmiva.controller;

import com.haushekmiva.dao.HibernateDao;
import com.haushekmiva.dao.PlayerHibernateDao;
import com.haushekmiva.model.Player;
import com.haushekmiva.service.OngoingMatchesService;
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

        System.out.println("Application started.");
    }

}