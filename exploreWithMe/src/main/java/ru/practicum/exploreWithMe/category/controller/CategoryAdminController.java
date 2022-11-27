package ru.practicum.exploreWithMe.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.category.dto.NewCategoryDto;
import ru.practicum.exploreWithMe.category.service.CategoryAdminService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService service;

    @PatchMapping("/admin/categories")
    public CategoryDto updateCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        CategoryDto dto = service.updateCategory(newCategoryDto);
        log.info("update Category: " + newCategoryDto);
        return dto;
    }

    @PostMapping("/admin/categories")
    public CategoryDto postCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        CategoryDto dto =  service.createCategory(newCategoryDto);
        log.info("post Category: " + newCategoryDto);
        return dto;
    }

    @DeleteMapping("/admin/categories/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("delete Category(id): " + catId);
        service.deleteCategory(catId);
    }
}
