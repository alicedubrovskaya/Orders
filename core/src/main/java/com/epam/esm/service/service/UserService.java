package com.epam.esm.service.service;

import com.epam.esm.service.dto.UserDto;

/**
 * Service defines user's operations
 */
public interface UserService {
    /**
     * Find user by id
     *
     * @param userId
     * @return found user
     */
    UserDto findById(Long userId);
}
