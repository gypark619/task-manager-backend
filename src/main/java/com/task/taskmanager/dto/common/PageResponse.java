package com.task.taskmanager.dto.common;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {

    private final List<T> content;
    private final int totalPages;
    private final long totalElements;
    private final int page;

    public PageResponse(List<T> content, int totalPages, long totalElements, int page) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.page = page;
    }
}