package com.epam.esm.service.repository.impl;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Tag;
import com.epam.esm.service.entity.enumeration.SortOrder;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.builder.CertificateQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public Certificate create(Certificate certificate) {
        List<Tag> tags = certificate.getTags().stream().map(t -> t.getId() != null ? em.merge(t) : t)
                .collect(Collectors.toList());
        certificate.setTags(tags);
        em.persist(certificate);
        return certificate;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        Certificate certificate = em.find(Certificate.class, id);
        return Optional.ofNullable(certificate);
    }

    @Override
    public List<Certificate> findByParamsAndSort(CertificateDto certificateDto,
                                                 Map<String, SortOrder> sortOrders,
                                                 PageDto pageDto) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CertificateQueryBuilder certificateQueryBuilder = new CertificateQueryBuilder();
        TypedQuery<Certificate> typedQuery = em.createQuery(
                certificateQueryBuilder.buildQueryFindByParamsWithSort(builder, certificateDto, sortOrders));
        typedQuery.setFirstResult((pageDto.getPage() - 1) * pageDto.getSize());
        typedQuery.setMaxResults(pageDto.getSize());
        return typedQuery.getResultList();
    }

    @Override
    public long countAllByParams(CertificateDto certificateDto, Map<String, SortOrder> sortOrders) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CertificateQueryBuilder certificateQueryBuilder = new CertificateQueryBuilder();
        TypedQuery<Certificate> typedQuery = em.createQuery(
                certificateQueryBuilder.buildQueryFindByParamsWithSort(builder, certificateDto, sortOrders));
        return typedQuery.getResultList().size();
    }

    @Override
    public Certificate update(Certificate certificate) {
        List<Tag> tags = certificate.getTags().stream().map(t -> t.getId() != null ? em.merge(t) : t)
                .collect(Collectors.toList());
        certificate.setTags(tags);
        return em.merge(certificate);
    }

    @Override
    public void delete(Long id) {
        Certificate certificate = em.find(Certificate.class, id);
        em.remove(certificate);
    }
}
