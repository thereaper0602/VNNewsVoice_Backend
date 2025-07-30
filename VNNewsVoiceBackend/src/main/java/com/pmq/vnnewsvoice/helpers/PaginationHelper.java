package com.pmq.vnnewsvoice.helpers;


import com.pmq.vnnewsvoice.utils.Pagination;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaginationHelper {
    public Pagination createPagination(Map<String, String> params, long totalItems, int pageSize) {
        String pageParam = params.getOrDefault("page", "1");
        return new Pagination(pageParam, totalItems, pageSize);
    }

    public Map<String,String> extractFilters(Map<String, String> params, String... filterKeys) {
        Map<String, String> filters = new java.util.HashMap<>();
        for (String key : filterKeys) {
            String mappedKey = switch (key) {
                case "categoryId" -> "categoryId";
                case "status" -> "status";
                case "search" -> "name";
                case "limit" -> "pageSize";
                default -> key;
            };
            if (params.containsKey(mappedKey)) {
                filters.put(mappedKey, params.get(mappedKey));
            }
        }
        return filters;

    }

}
