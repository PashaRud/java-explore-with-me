package ru.practicum.exploreWithMe.service.category;

import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.categories.NewCategoryDto;

public interface CategoryAdminService {

    CategoryDto updateCategory(CategoryDto dto);

    CategoryDto createCategory(NewCategoryDto dto);

    void deleteCategory(Long catId);
}
