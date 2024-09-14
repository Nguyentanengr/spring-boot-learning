package com.anonymous.shop_application.controllers;

import com.anonymous.shop_application.dtos.requests.CategoryRequest;
import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.dtos.responses.CategoryResponse;
import com.anonymous.shop_application.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @GetMapping("/{id}")
    ApiResponse<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .value(categoryService.getCategoryById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .value(categoryService.getAllCategories())
                .build();
    }

    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .value(categoryService.createCategory(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .value(categoryService.updateCategory(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
