package ru.practicum.exploreWithMe.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.categories.NewCategoryDto;
import ru.practicum.exploreWithMe.exception.AlreadyExistsException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
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
    @Transactional
    public CategoryDto updateCategory(NewCategoryDto dto) {
        try {
            log.info("Upd category: " + dto.toString());
            return  CategoryMapper.toCategoryDto(repository.save(CategoryMapper.NewCategoryDtoToCategory(dto)));
        } catch (RuntimeException e) {
            throw new AlreadyExistsException("Name must be unique.");
        }
//        category = repository.save(category);

//        if (dto.getId() == null || !repository.existsById(dto.getId())) {
//            throw new ValidateException("Категория не найдена");
//        }
//        Category category = repository.findById(dto.getId()).get();
//        if (dto.getName() != null) {
//            category.setName(dto.getName());
//        }
//        category = repository.save(category);
//
//        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto dto) {
        try {
            log.info("Create category: " + dto.toString());
            return  CategoryMapper.toCategoryDto(repository.save(CategoryMapper.NewCategoryDtoToCategory(dto)));
        } catch (RuntimeException e) {
            throw new AlreadyExistsException("Name must be unique.");
        }
    }

    @Override
    public void deleteCategory(Long catId) {

        repository.deleteById(catId);
    }
}
