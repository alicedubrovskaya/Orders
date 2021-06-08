package com.epam.esm.service.repository.impl;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Tag;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private EntityManager em;
    private final CertificateRepository certificateRepository;

    @Autowired
    public TagRepositoryImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Tag create(Tag tag) {
        em.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Tag tag = em.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));

        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Tag> findByNames(List<String> tagNames) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).where(root.get("name").in(tagNames));
        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

//    @Override
//    public Optional<Tag> findMostPopular() {
//        try {
//            Query query = em.createNativeQuery(
//                    "SELECT tag.id, tag.name FROM tag JOIN certificate_tag ON tag.id=certificate_tag.tag_id " +
//                            "JOIN certificate ON certificate_tag.tag_id = certificate.id " +
//                            "JOIN ordr ON certificate.id = ordr.id " +
//                            "JOIN  user ON ordr.user_id=user.id WHERE user.id= (" +
//                            "SELECT user.id FROM user JOIN ordr on user.id = ordr.user_id GROUP BY user.id " +
//                            "ORDER BY SUM(cost) DESC LIMIT 1) " +
//                            "GROUP BY tag.name, tag.id ORDER BY COUNT(tag.name) DESC LIMIT 1",
//                    Tag.class);
//            Tag tag = (Tag) query.getSingleResult();
//            return Optional.ofNullable(tag);
//        } catch (NoResultException e) {
//            return Optional.empty();
//        }
//    }


    @Override
    public Optional<Tag> findMostPopular() {
        try {
            Query query = em.createNativeQuery(
                    "SELECT tag.id, tag.name FROM tag JOIN certificate_tag ON tag.id=certificate_tag.tag_id " +
                            "JOIN certificate ON certificate_tag.certificate_id = certificate.id " +
                            "JOIN ordr ON certificate.id = ordr.certificate_id " +
                            "JOIN  user ON ordr.user_id=user.id WHERE user.id= (" +
                            "SELECT user.id FROM user JOIN ordr on user.id = ordr.user_id GROUP BY user.id " +
                            "ORDER BY SUM(cost) DESC LIMIT 1) " +
                            "GROUP BY tag.name, tag.id ORDER BY COUNT(tag.name) DESC LIMIT 1",
                    Tag.class);
            Tag tag = (Tag) query.getSingleResult();
            return Optional.ofNullable(tag);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll(PageDto pageDto) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);

        TypedQuery<Tag> query = em.createQuery(criteriaQuery);
        query.setFirstResult((pageDto.getPage() - 1) * pageDto.getSize());
        query.setMaxResults(pageDto.getSize());
        return query.getResultList();
    }

    @Override
    public long countAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public void delete(Long id) {
        Tag tag = em.find(Tag.class, id);
        List<Certificate> tagCertificates = tag.getCertificates();
        tagCertificates.forEach(certificate -> certificate.getTags().remove(tag));
        tagCertificates.forEach(certificateRepository::update);
        em.remove(tag);
    }
}
