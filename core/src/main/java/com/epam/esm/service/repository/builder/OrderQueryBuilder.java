package com.epam.esm.service.repository.builder;

import com.epam.esm.service.entity.Order;
import com.epam.esm.service.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class OrderQueryBuilder {

    public CriteriaQuery<Order> buildQueryFindByUser(CriteriaBuilder criteriaBuilder,
                                                     User user) {
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<com.epam.esm.service.entity.Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), user));
        return criteriaQuery;
    }

    public CriteriaQuery<Long> buildQueryCalculateByUser(CriteriaBuilder criteriaBuilder,
                                                          User user) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.count(root)).where(criteriaBuilder.equal(root.get("user"), user));
        return criteriaQuery;
    }
}
