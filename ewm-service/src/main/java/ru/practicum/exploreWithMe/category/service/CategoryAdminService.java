package ru.practicum.exploreWithMe.category.service;

import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.category.dto.NewCategoryDto;

public interface CategoryAdminService {

    CategoryDto updateCategory(NewCategoryDto dto);

    CategoryDto createCategory(NewCategoryDto dto);

    void deleteCategory(Long catId);
}
