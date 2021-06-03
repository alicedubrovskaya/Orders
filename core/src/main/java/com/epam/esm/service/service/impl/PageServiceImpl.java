package com.epam.esm.service.service.impl;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.entity.enumeration.SortOrder;
import com.epam.esm.service.exception.PageNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.OrderRepository;
import com.epam.esm.service.repository.TagRepository;
import com.epam.esm.service.repository.UserRepository;
import com.epam.esm.service.service.PageService;
import com.epam.esm.service.service.builder.SearchParamsBuilder;
import com.epam.esm.service.service.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Implementation of page service
 */
@Service
public class PageServiceImpl implements PageService {
    private final OrderRepository orderRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;

    @Autowired
    public PageServiceImpl(OrderRepository orderRepository, TagRepository tagsRepository, UserRepository userRepository,
                           CertificateRepository certificateRepository) {
        this.orderRepository = orderRepository;
        this.tagRepository = tagsRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public Page buildOrdersPage(PageDto pageDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NO_USER_WITH_ID, userId));

        long countOfOrders = orderRepository.countAllByUser(user);
        if (countOfOrders <= neededCountOfItems(pageDto)) {
            throw new PageNotFoundException(ErrorMessage.PAGE_NOT_FOUND, ErrorCode.NO_PAGE_WITH_ORDERS);
        }
        return new Page(countOfOrders, countOfPages(countOfOrders, pageDto));
    }

    @Override
    public Page buildTagsPage(PageDto pageDto) {
        long countOfTags = tagRepository.countAll();
        if (countOfTags <= neededCountOfItems(pageDto)) {
            throw new PageNotFoundException(ErrorMessage.PAGE_NOT_FOUND, ErrorCode.NO_PAGE_WITH_TAGS);
        }
        return new Page(countOfTags, countOfPages(countOfTags, pageDto));
    }

    @Override
    public Page buildCertificatesPage(PageDto pageDto, SearchCertificateDto searchCertificateDto) {
        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        CertificateDto certificateDto = searchParamsBuilder.buildCertificate(searchCertificateDto);
        Map<String, SortOrder> sortOrders = searchParamsBuilder.buildSortParams(searchCertificateDto);

        long countOfCertificates = certificateRepository.countAllByParams(certificateDto, sortOrders);
        if (countOfCertificates <= neededCountOfItems(pageDto)) {
            throw new PageNotFoundException(ErrorMessage.PAGE_NOT_FOUND, ErrorCode.NO_PAGE_WITH_CERTIFICATES);
        }
        return new Page(countOfCertificates, countOfPages(countOfCertificates, pageDto));
    }

    private long countOfPages(long countOfItems, PageDto pageDto) {
        return countOfItems % pageDto.getSize() == 0
                ? countOfItems / pageDto.getSize() : countOfItems / pageDto.getSize() + 1;
    }

    private long neededCountOfItems(PageDto pageDto) {
        return (long) (pageDto.getPage() - 1) * pageDto.getSize();
    }
}
