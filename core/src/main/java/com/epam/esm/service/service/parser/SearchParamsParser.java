package com.epam.esm.service.service.parser;


import com.epam.esm.service.entity.enumeration.SortOrder;

import java.util.*;

public class SearchParamsParser {
    public List<String> parseTagNames(String param) {
        List<String> names = new ArrayList<>();
        if (param != null) {
            names.addAll(Arrays.asList(param.split(",")));
        }
        return names;
    }

    public Map<String, SortOrder> parseSortParams(String param) {
        Map<String, SortOrder> sortParams = new HashMap<>();
        if (param != null) {
            String[] params = param.split(",");
            Arrays.stream(params).forEach(sortOrder -> {
                if (sortOrder.startsWith("desc")) {
                    sortParams.put(sortOrder.substring(5, sortOrder.length() - 1), SortOrder.DESC);
                } else {
                    sortParams.put(sortOrder.substring(4, sortOrder.length() - 1), SortOrder.ASC);
                }
            });
        }
        return sortParams;
    }

    public List<String> parseFieldsToSortBy(String param) {
        List<String> fields = new ArrayList<>();
        if (param != null) {
            fields.addAll(Arrays.asList(param.split(",")));
        }
        return fields;
    }

    public List<String> parseOrdersWithFieldsToFields(String param) {
        List<String> fields = new ArrayList<>();
        if (param != null) {
            String[] params = param.split(",");
            for (String sortWithField : params) {
                fields.add(sortWithField.substring(1));
            }
        }
        return fields;
    }

    public Map<String, SortOrder> parseSortOrders(String param) {
        Map<String, SortOrder> sortOrders = new HashMap<>();
        if (param != null) {
            String[] params = param.split(",");
            Arrays.stream(params).forEach(sortOrder -> {
                if (sortOrder.charAt(0) == '-') {
                    sortOrders.put(sortOrder.substring(1), SortOrder.DESC);
                } else {
                    sortOrders.put(sortOrder.substring(1), SortOrder.ASC);
                }
            });
        }
        return sortOrders;
    }
}
