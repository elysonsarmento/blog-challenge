package me.desafio.blog.dto;

import org.springframework.http.codec.ServerSentEvent;

import java.util.List;

public record ApiResponse<T>(PaginationRespone pagination,List<T> data ) {
}
