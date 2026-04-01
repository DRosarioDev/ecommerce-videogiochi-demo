package com.rosariodev.videogames.persistence.hibernate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Review;
import com.rosariodev.videogames.persistence.IDAOReview;

@Repository
@Profile("hibernate")
public class DAOReviewHibernate extends DAOGenericHibernate<Review> implements IDAOReview {
    
}
