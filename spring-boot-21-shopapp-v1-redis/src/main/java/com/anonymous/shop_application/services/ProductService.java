package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.ProductRemoveImageRequest;
import com.anonymous.shop_application.dtos.requests.ProductRequest;
import com.anonymous.shop_application.dtos.requests.ProductUploadImageRequest;
import com.anonymous.shop_application.dtos.responses.ProductImageResponse;
import com.anonymous.shop_application.dtos.responses.ProductListResponse;
import com.anonymous.shop_application.dtos.responses.ProductResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.mappers.ProductMapper;
import com.anonymous.shop_application.models.Category;
import com.anonymous.shop_application.models.Product;
import com.anonymous.shop_application.models.ProductImage;
import com.anonymous.shop_application.repositories.CategoryRepository;
import com.anonymous.shop_application.repositories.ProductImageRepository;
import com.anonymous.shop_application.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ProductService {

    @NonFinal
    static final String UPLOAD_FOLDER = "uploads";
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryService categoryService;
    ProductImageRepository productImageRepository;

    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);
        return productMapper.mapToProductResponse(product);
    }

    public ProductListResponse getAllProducts(String keyword, Long categoryId, Pageable pageable) {
        Page<Product> page = productRepository
                .findAllProductByFilter(keyword, categoryId, pageable);

        return ProductListResponse.builder()
                .products(page
                        .map(productMapper::mapToProductResponse)
                        .stream().collect(Collectors.toList()))
                .totalPages(page.getTotalPages())
                .build();
    }

    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryService.findCategoryById(request.getCategory());
        Product product = productMapper.mapToProduct(request);
        product.setCategory(category);

        return productMapper.mapToProductResponse(productRepository.save(product));
    }

    public ProductResponse updatedProduct(Long id, ProductRequest request) {
        Product product = findProductById(id);
        Category category = categoryService.findCategoryById(id);

        productMapper.updateToProduct(request, product);
        product.setCategory(category);

        return productMapper.mapToProductResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        Product product = findProductById(id);

        productRepository.delete(product);
    }

    public ProductResponse uploadImage(Long productId, ProductUploadImageRequest request) throws IOException {
        Product product = findProductById(productId);

        if (request.getFiles().size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT - product.getProductImages().size()) {
            throw new AppException(ErrorCode.PRODUCT_IMAGE_FILE_INVALID_SIZE);
        }

        List<String> urls = storeFiles(request.getFiles());

        List<ProductImage> productImages = new ArrayList<>(product.getProductImages());
        urls.forEach(url -> productImages.add(
                ProductImage.builder()
                        .imageUrl(url)
                        .product(product)
                        .build()
        ));
        product.setProductImages(productImages);

        return productMapper.mapToProductResponse(productRepository.save(product));
    }

    public void removeImage(ProductRemoveImageRequest request) {
        List<ProductImage> productImages = productImageRepository.findAllById(request.getImageIds());

        productImages.forEach(productImage -> {
            productImageRepository.delete(productImage);

            try {
                deleteFile(productImage.getImageUrl());
            } catch (Exception e) {
                throw new AppException(ErrorCode.PRODUCT_IMAGE_ERROR_DELETE);
            }
        });
    }

    private List<String> storeFiles(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
            String uniqueFilename = UUID.randomUUID() + "_" + filename;
            urls.add(uniqueFilename);

            // Đường dẫn đến thư mục mà bạn muốn lưu file
            Path uploadDirectory = Paths.get(UPLOAD_FOLDER);

            // Kiểm tra và tạo thư mục nếu nó không tồn tại
            if (!Files.exists(uploadDirectory)) Files.createDirectories(uploadDirectory);

            // Đường dẫn đầy đủ đến file
            Path destination = Paths.get(uploadDirectory.toString(), uniqueFilename);

            // Sao chép file vào thư mục đích
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
        return urls;
    }

    public void deleteFile(String url) throws IOException {

        // Duong dan den thuc muc chua url
        Path uploadDirectory = Paths.get(UPLOAD_FOLDER);

        // Duong dan day du den file can xoa
        Path filePath = uploadDirectory.resolve(url);

        // kiem tra xem file co ton tai khong
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new FileNotFoundException("File not found: " + url);
        }
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

    }

    public boolean existsByName(String productName) {
        return productRepository.existsByName(productName);
    }
}
