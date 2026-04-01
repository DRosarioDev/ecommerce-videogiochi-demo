package com.rosariodev.videogames.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Product;
import com.rosariodev.videogames.persistence.DAOException;
import com.rosariodev.videogames.persistence.IDAOProduct;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Repository
@Profile("hibernate")
public class DAOProductHibernate extends DAOGenericHibernate<Product> implements IDAOProduct{

    @Override
    public List<Product> findByName(String name) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null && !name.trim().isEmpty()) {
            predicates.add(builder.equal(root.get("name"), name));
        }
        query.where(predicates.toArray(new Predicate[]{}));
        return getSession().createQuery(query).getResultList();
    }
    
}
