package com.rosariodev.videogames.persistence.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Purchase;
import com.rosariodev.videogames.persistence.IDAOPurchase;

@Repository
@Profile("mock")
public class DAOPurchaseMock extends DAOGenericMock<Purchase> implements IDAOPurchase {
    
    

}
