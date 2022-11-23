package ru.practicum.exploreWithMe.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.categories.NewCategoryDto;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.mapper.categories.CategoryMapper;
import ru.practicum.exploreWithMe.model.Category;
import ru.practicum.exploreWithMe.repository.category.CategoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService{

    private final CategoryRepository repository;

    @Override
    public CategoryDto updateCategory(CategoryDto dto) {

        Category category = repository.findById(dto.getId())
                .orElseThrow(() ->
                        new ValidateException("Category with id = " + dto.getId() + " not found"));

        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        category = repository.save(category);

        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto dto) {
        Category category = CategoryMapper.NewCategorytoDtoToCategory(dto);
        category = repository.save(category);
        return CategoryMapper.toCategoryDto(category);    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() ->
                        new ValidateException("Category with id = " + catId + " not found"));
        repository.deleteById(catId);
    }
}
