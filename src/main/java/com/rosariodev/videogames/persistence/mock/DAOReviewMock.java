package com.rosariodev.videogames.persistence.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.Review;
import com.rosariodev.videogames.persistence.IDAOReview;

@Repository
@Profile("mock")
public class DAOReviewMock extends DAOGenericMock<Review> implements IDAOReview {
    
}
