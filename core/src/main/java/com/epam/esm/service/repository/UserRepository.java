package com.epam.esm.service.repository;

import com.epam.esm.service.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
}
