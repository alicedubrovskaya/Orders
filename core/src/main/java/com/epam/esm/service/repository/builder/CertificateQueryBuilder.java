package com.epam.esm.service.repository.builder;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.enumeration.SortOrder;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CertificateQueryBuilder {

    public CriteriaQuery<Certificate> buildQueryFindByParamsWithSort(CriteriaBuilder builder,
                                                                     CertificateDto certificateDto,
                                                                     Map<String, SortOrder> sortOrders) {
        CriteriaQuery<Certificate> query = builder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);

        buildQuerySearchByParams(query, root, builder, certificateDto);
        buildQuerySort(query, root, builder, sortOrders);
        return query;
    }

    public CriteriaQuery<Certificate> buildQuerySearchByParams(CriteriaQuery<Certificate> query,
                                                               Root<Certificate> root,
                                                               CriteriaBuilder builder,
                                                               CertificateDto certificateDto) {
        List<Predicate> predicates = new ArrayList<>();
        PredicateBuilder predicateBuilder = new PredicateBuilder();
        predicateBuilder.getNamePredicate(certificateDto.getName(), builder, root).ifPresent(predicates::add);
        predicateBuilder.getDescriptionPredicate(certificateDto.getDescription(), builder, root).ifPresent(predicates::add);
        predicateBuilder.getTagPredicate(certificateDto.getTags(), root).ifPresent(predicates::add);
        return query.where(predicates.toArray(new Predicate[0]));
    }

    public CriteriaQuery<Certificate> buildQuerySort(CriteriaQuery<Certificate> query,
                                                     Root<Certificate> root,
                                                     CriteriaBuilder builder,
                                                     Map<String, SortOrder> sortOrders) {
        if (!sortOrders.isEmpty()) {
            List<Order> ordersForQuery = new ArrayList<>();
            for (Map.Entry<String, SortOrder> entry : sortOrders.entrySet()) {
                ordersForQuery.add(entry.getValue().equals(SortOrder.DESC) ?
                        builder.desc(root.get(entry.getKey())) : builder.asc(root.get(entry.getKey())));
            }
            query.orderBy(ordersForQuery);
        }
        return query;
    }
}
