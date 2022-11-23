package ru.practicum.exploreWithMe.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.categories.NewCategoryDto;
import ru.practicum.exploreWithMe.service.category.CategoryAdminService;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService service;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto dto) {
        return service.updateCategory(dto);
    }

    @PostMapping
    public CategoryDto postCategory(@RequestBody NewCategoryDto dto) {
        return service.createCategory(dto);
    }

    @DeleteMapping("{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        service.deleteCategory(catId);
    }
}
