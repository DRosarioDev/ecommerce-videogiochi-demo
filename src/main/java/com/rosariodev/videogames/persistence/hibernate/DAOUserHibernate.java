package com.rosariodev.videogames.persistence.hibernate;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.User;
import com.rosariodev.videogames.persistence.DAOException;
import com.rosariodev.videogames.persistence.IDAOUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
@Profile("hibernate")
public class DAOUserHibernate extends DAOGenericHibernate<User> implements IDAOUser {

    @Override
    public User getUserByEmail(String email) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Predicate predicateEmail = builder.equal(root.get("email"), email);
        query.where(predicateEmail);

        List<User> results = getSession().createQuery(query).getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

}
