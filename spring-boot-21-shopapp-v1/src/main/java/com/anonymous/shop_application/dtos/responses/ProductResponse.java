package com.anonymous.shop_application.dtos.responses;

import com.anonymous.shop_application.models.Category;
import com.anonymous.shop_application.models.Comment;
import com.anonymous.shop_application.models.ProductImage;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    Long id;

    String name;

    Float price;

    String thumbnail;

    String description;

    Category category;

    List<ProductImageResponse> productImages;

    List<Comment> comments; //can chinh response
}
