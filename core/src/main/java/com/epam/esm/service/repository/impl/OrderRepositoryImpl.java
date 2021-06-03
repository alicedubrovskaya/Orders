package com.epam.esm.service.repository.impl;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Order;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.repository.OrderRepository;
import com.epam.esm.service.repository.builder.OrderQueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of user repository
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Order create(Order order) {
        em.persist(order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    @Override
    public List<Order> findByUser(User user, PageDto pageDto) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        OrderQueryBuilder orderQueryBuilder = new OrderQueryBuilder();
        TypedQuery<Order> query = em.createQuery(orderQueryBuilder.buildQueryFindByUser(criteriaBuilder, user));
        query.setFirstResult((pageDto.getPage() - 1) * pageDto.getSize());
        query.setMaxResults(pageDto.getSize());
        return query.getResultList();
    }

    @Override
    public List<Order> findByCertificate(Certificate certificate) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("certificate"), certificate));
        TypedQuery<Order> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public long countAllByUser(User user) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        OrderQueryBuilder orderQueryBuilder = new OrderQueryBuilder();
        return em.createQuery(orderQueryBuilder.buildQueryCalculateByUser(criteriaBuilder, user)).getSingleResult();
    }
}
