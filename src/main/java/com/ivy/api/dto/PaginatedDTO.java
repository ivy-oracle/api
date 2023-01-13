package com.ivy.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class PaginatedDTO<T> {
    @NotNull
    private List<T> data;

    @NotNull
    private int page;

    @NotNull
    private int limit;

    @NotNull
    private long totalCount;

    @NotNull
    private boolean hasMore;

    public PaginatedDTO() {
        this.data = new ArrayList<>();
    }

    public PaginatedDTO(Page<T> page) {
        this.data = page.getContent();
        this.limit = page.getSize();
        this.totalCount = page.getTotalElements();
        this.hasMore = page.hasNext();
    }
}
