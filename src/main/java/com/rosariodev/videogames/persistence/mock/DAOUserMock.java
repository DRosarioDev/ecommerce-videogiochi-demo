package com.rosariodev.videogames.persistence.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.User;
import com.rosariodev.videogames.persistence.DAOException;
import com.rosariodev.videogames.persistence.IDAOUser;

@Repository
@Profile("mock")
public class DAOUserMock  extends DAOGenericMock<User> implements IDAOUser {

    @Override
    public User getUserByEmail(String email) throws DAOException {
        for (User user : this.findAll()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
        
    
}
