package com.anonymous.shop_application.controllers;


import com.anonymous.shop_application.dtos.requests.ProductRemoveImageRequest;
import com.anonymous.shop_application.dtos.requests.ProductRequest;
import com.anonymous.shop_application.dtos.requests.ProductUploadImageRequest;
import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.dtos.responses.ProductListResponse;
import com.anonymous.shop_application.dtos.responses.ProductResponse;
import com.anonymous.shop_application.services.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long id) {
        return ApiResponse.<ProductResponse>builder()
                .value(productService.getProductById(id))
                .build();
    }


    @GetMapping
    public ApiResponse<ProductListResponse> getAllProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "id") String fieldSorted,
            @RequestParam(defaultValue = "") String sorter)
    {
        PageRequest pageRequest = PageRequest.of(page, limit, sorter.equals("desc")
                        ? Sort.by(fieldSorted).descending()
                        : Sort.by(fieldSorted).ascending()
        );
        return ApiResponse.<ProductListResponse>builder()
                .value(productService.getAllProducts(keyword, categoryId, pageRequest))
                .build();
    }


    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .value(productService.createProduct(request))
                .build();
    }


    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductRequest request)
    {
        return ApiResponse.<ProductResponse>builder()
                .value(productService.updatedProduct(id, request))
                .build();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .build();
    }


    @PostMapping("/uploadImage/{id}")
    public ApiResponse<ProductResponse> uploadImage(
            @PathVariable("id") Long productId,
            @ModelAttribute @Valid ProductUploadImageRequest request) throws IOException
    {
        return ApiResponse.<ProductResponse>builder()
                .value(productService.uploadImage(productId, request))
                .build();
    }


    @PostMapping("/removeImage")
    public ApiResponse<Void> removeImage(@RequestBody @Valid ProductRemoveImageRequest request) {
        productService.removeImage(request);
        return ApiResponse.<Void>builder()
                .build();
    }


    @PostMapping("/generateFakeProducts")
    private ApiResponse<Void> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 6_000; i++) {
            String productName = faker.commerce().productName();
            if (productService.existsByName(productName)) {
                continue;
            }
            ProductRequest productRequest = ProductRequest.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(1_000_000, 90_000_000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .category((long) faker.number().numberBetween(1, 5))
                    .build();
            productService.createProduct(productRequest);
        }
        return ApiResponse.<Void>builder()
                .message("Successfully generate fake products")
                .build();
    }
}
