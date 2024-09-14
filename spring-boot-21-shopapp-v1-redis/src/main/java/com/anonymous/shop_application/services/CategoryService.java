package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.CategoryRequest;
import com.anonymous.shop_application.dtos.responses.CategoryResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.DBException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.mappers.CategoryMapper;
import com.anonymous.shop_application.models.Category;
import com.anonymous.shop_application.repositories.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    public CategoryResponse getCategoryById(Long id) {
        Category category = findCategoryById(id);
        return categoryMapper.mapToCategoryResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);

        Category category = categoryMapper.mapToCategory(request);
        return categoryMapper.mapToCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = findCategoryById(id);

        categoryMapper.updateToCategory(request, category);
        return categoryMapper.mapToCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        Category category = findCategoryById(id);
        try {
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new DBException(ErrorCode.CATEGORY_NOT_DELETE);
        }
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
