package com.anonymous.shop_application.mappers;

import com.anonymous.shop_application.dtos.requests.CategoryRequest;
import com.anonymous.shop_application.dtos.responses.CategoryResponse;
import com.anonymous.shop_application.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CategoryMapper{

    Category mapToCategory(CategoryRequest request);

    Category updateToCategory(CategoryRequest request, @MappingTarget Category category);

    CategoryResponse mapToCategoryResponse(Category category);
}
