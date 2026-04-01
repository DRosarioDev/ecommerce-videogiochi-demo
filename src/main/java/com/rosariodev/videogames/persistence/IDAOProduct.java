package com.rosariodev.videogames.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Product;

@Repository
public interface IDAOProduct extends IDAOGeneric<Product> {
    
    List<Product> findByName(String name) throws DAOException;

}
