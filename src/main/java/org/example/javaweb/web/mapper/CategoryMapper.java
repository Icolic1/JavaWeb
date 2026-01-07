package org.example.javaweb.web.mapper;

import org.example.javaweb.domain.Category;
import org.example.javaweb.web.dto.CategoryDto;

public final class CategoryMapper {
    private CategoryMapper() {}

    public static CategoryDto toDto(Category c) {
        return new CategoryDto(c.getId(), c.getName());
    }
}
