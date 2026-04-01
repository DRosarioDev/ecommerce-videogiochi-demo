package com.rosariodev.videogames.persistence.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Order;
import com.rosariodev.videogames.persistence.IDAOOrder;

@Repository
@Profile("mock")
public class DAOOrderMock extends DAOGenericMock<Order> implements IDAOOrder{
    
}
