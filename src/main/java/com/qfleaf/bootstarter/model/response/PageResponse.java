package com.qfleaf.bootstarter.model.response;

import lombok.Data;

import java.util.List;

/**
 * 通用分页响应类
 *
 * @param <T> 数据列表中元素的类型
 */
@Data
public class PageResponse<T> {
    private List<T> records;
    private Long total;
    private Long page;
    private Long size;
    private Integer pages;

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private List<T> records;
        private Long total;
        private Long page;
        private Long size;

        private Builder() {
        }

        public Builder<T> records(List<T> records) {
            this.records = records;
            return this;
        }

        public Builder<T> total(Long total) {
            this.total = total;
            return this;
        }

        public Builder<T> page(Long page) {
            this.page = page;
            return this;
        }

        public Builder<T> size(Long size) {
            this.size = size;
            return this;
        }


        public PageResponse<T> build() {
            return new PageResponse<>(records, total, page, size);
        }
    }

    public PageResponse(List<T> records, Long total, Long page, Long size) {
        this.records = records;
        this.total = total;
        this.page = page;
        this.size = size;
        this.pages = (int) ((total + size - 1) / size); // 计算总页数
    }
}

