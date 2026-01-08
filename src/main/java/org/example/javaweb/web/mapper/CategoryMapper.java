package org.example.javaweb.web.mapper;

import org.example.javaweb.domain.Category;
import org.example.javaweb.web.dto.CategoryDto;
import org.example.javaweb.web.dto.CategoryFormDto;

public final class CategoryMapper {
    private CategoryMapper() {}

    public static CategoryDto toDto(Category c) {
        return new CategoryDto(c.getId(), c.getName());
    }

    public static CategoryFormDto toFormDto(Category c) {
        CategoryFormDto dto = new CategoryFormDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        return dto;
    }

    public static Category toEntity(CategoryFormDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }


}




