package ru.practicum.exploreWithMe.service.category;

import ru.practicum.exploreWithMe.dto.categories.CategoryDto;

import java.util.List;

public interface CategoryPublicService {

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(Long catId);
}
