package ru.practicum.exploreWithMe.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.category.dto.NewCategoryDto;
import ru.practicum.exploreWithMe.exception.AlreadyExistsException;
import ru.practicum.exploreWithMe.category.repository.CategoryRepository;

import static ru.practicum.exploreWithMe.category.mapper.CategoryMapper.NewCategoryDtoToCategory;
import static ru.practicum.exploreWithMe.category.mapper.CategoryMapper.toCategoryDto;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService{

    private final CategoryRepository repository;

    @Override
    @Transactional
    public CategoryDto updateCategory(NewCategoryDto dto) {
        try {
            log.info("Upd category: " + dto.toString());
            return  toCategoryDto(repository.save(NewCategoryDtoToCategory(dto)));
        } catch (RuntimeException e) {
            throw new AlreadyExistsException("Name must be unique.");
        }
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto dto) {
        try {
            log.info("Create category: " + dto.toString());
            return  toCategoryDto(repository.save(NewCategoryDtoToCategory(dto)));
        } catch (RuntimeException e) {
            throw new AlreadyExistsException("Name must be unique.");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long catId) {
        log.info("delete category: " + catId);
        repository.deleteById(catId);
    }
}
