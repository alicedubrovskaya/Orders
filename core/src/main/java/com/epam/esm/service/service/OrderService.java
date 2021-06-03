package com.epam.esm.service.service;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PageDto;

import javax.validation.Valid;
import java.util.List;

/**
 * Service defines operations for order
 */
public interface OrderService {
    /**
     * Creates order
     *
     * @param userId
     * @param certificateId
     * @return
     */
    OrderDto makeOrder(Long userId, Long certificateId);

    /**
     * Finds order by id
     *
     * @param orderId
     * @return found order
     */
    OrderDto findById(Long orderId);

    /**
     * Finds orders by userId
     *
     * @param userId
     * @param pageDto
     * @return
     */
    List<OrderDto> findByUser(Long userId, @Valid PageDto pageDto);
}
