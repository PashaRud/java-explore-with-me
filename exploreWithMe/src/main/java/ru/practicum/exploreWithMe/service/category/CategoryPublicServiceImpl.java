package ru.practicum.exploreWithMe.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.mapper.categories.CategoryMapper;
import ru.practicum.exploreWithMe.model.Category;
import ru.practicum.exploreWithMe.repository.category.CategoryRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {

    private final CategoryRepository repository;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        List<CategoryDto> categoryDtos = repository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new ValidateException("Category id = " + catId + "not found"));
        return CategoryMapper.toCategoryDto(category);    }
}
