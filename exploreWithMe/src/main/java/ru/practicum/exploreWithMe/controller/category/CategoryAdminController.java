package ru.practicum.exploreWithMe.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.categories.NewCategoryDto;
import ru.practicum.exploreWithMe.service.category.CategoryAdminService;

import javax.validation.Valid;

@RestController
@Slf4j
//@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService service;

    @PatchMapping("/admin/categories")
    public CategoryDto updateCategory(@Valid @RequestBody NewCategoryDto dto) {
        return service.updateCategory(dto);
    }

    @PostMapping("/admin/categories")
    public CategoryDto postCategory(@Valid @RequestBody NewCategoryDto dto) {
        return service.createCategory(dto);
    }

    @DeleteMapping("/admin/categories/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        service.deleteCategory(catId);
    }
}
