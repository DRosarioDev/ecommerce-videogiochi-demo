package com.rosariodev.videogames.persistence;

import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Order;

@Repository
public interface IDAOOrder extends IDAOGeneric<Order> {
    
}
