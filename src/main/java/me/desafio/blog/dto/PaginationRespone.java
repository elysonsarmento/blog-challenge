package me.desafio.blog.dto;

import org.springframework.data.domain.Page;

public record  PaginationRespone (int totalPages, long totalElements, int currentPage, int pageSize) {


    public static PaginationRespone fromPage(Page<?> page) {
        return new PaginationRespone(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }
}
