package com.anonymous.shop_application.mappers;

import com.anonymous.shop_application.dtos.requests.ProductRequest;
import com.anonymous.shop_application.dtos.responses.ProductResponse;
import com.anonymous.shop_application.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    Product mapToProduct(ProductRequest productRequest);

    @Mapping(target = "category", ignore = true)
    Product updateToProduct(ProductRequest productRequest, @MappingTarget Product product);

    ProductResponse mapToProductResponse(Product product);
}
