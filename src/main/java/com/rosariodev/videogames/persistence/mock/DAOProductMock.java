package com.rosariodev.videogames.persistence.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Product;
import com.rosariodev.videogames.persistence.DAOException;
import com.rosariodev.videogames.persistence.IDAOProduct;

@Repository
@Profile("mock")
public class DAOProductMock extends DAOGenericMock<Product> implements IDAOProduct {
    
    public List<Product> findByName(String name) throws DAOException {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : findAll()) {
            if (product.getName().contains(name)) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }

}