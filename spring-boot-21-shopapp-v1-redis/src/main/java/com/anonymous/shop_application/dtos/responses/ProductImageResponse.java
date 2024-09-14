package com.anonymous.shop_application.dtos.responses;

import com.anonymous.shop_application.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageResponse {

    Long id;

    String imageUrl;
}
