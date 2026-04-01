package com.rosariodev.videogames.persistence.hibernate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Order;
import com.rosariodev.videogames.persistence.IDAOOrder;

@Repository
@Profile("hibernate")
public class DAOOrderHibernate extends DAOGenericHibernate<Order> implements IDAOOrder {
    
}
