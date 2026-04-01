package com.rosariodev.videogames.persistence.hibernate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Purchase;
import com.rosariodev.videogames.persistence.IDAOPurchase;


@Repository
@Profile("hibernate")
public class DAOPurchaseHibernate extends DAOGenericHibernate<Purchase> implements IDAOPurchase {
    
}
