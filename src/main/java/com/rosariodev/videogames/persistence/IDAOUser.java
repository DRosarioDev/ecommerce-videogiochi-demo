package com.rosariodev.videogames.persistence;

import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.model.User;


@Repository
public interface IDAOUser extends IDAOGeneric<User>{
    
    User getUserByEmail(String email) throws DAOException;

}
