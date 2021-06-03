package com.epam.esm.service.repository.impl;

import com.epam.esm.service.entity.User;
import com.epam.esm.service.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * Implementation of user repository
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class,id));
    }
}
