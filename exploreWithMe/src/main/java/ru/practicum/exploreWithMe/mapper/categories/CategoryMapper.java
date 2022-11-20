package ru.practicum.exploreWithMe.mapper.categories;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.model.Category;

@Component
public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(CategoryDto dto) {
        return new Category(dto.getId(), dto.getName());
    }
}
