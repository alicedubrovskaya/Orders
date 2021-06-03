package com.epam.esm.service.repository;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Order;
import com.epam.esm.service.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order create(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUser(User user, PageDto pageDto);

    List<Order> findByCertificate(Certificate certificate);

    long countAllByUser(User user);
}
