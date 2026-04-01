package com.rosariodev.videogames.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.rosariodev.videogames.model.User;
import com.rosariodev.videogames.persistence.DAOException;
import com.rosariodev.videogames.persistence.IDAOUser;
import com.rosariodev.videogames.persistence.hibernate.DAOUtilHibernate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IDAOUser daoUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            DAOUtilHibernate.beginTransaction();
            User user = daoUser.getUserByEmail(email);
            DAOUtilHibernate.commitTransaction();
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + email);
            }
            return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw new UsernameNotFoundException("Impossible to find the user: " + email, ex);
        }
    }
}