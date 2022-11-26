package ru.practicum.exploreWithMe.category.service;

import ru.practicum.exploreWithMe.category.dto.CategoryDto;

import java.util.List;

public interface CategoryPublicService {

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(Long catId);
}
