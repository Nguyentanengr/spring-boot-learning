package com.anonymous.shop_application.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "PRODUCT_NAME_INVALID_BLANK")
    @Size(min = 3, max = 200, message = "PRODUCT_NAME_INVALID_SIZE")
    String name;

    @Min(value = 0, message = "PRODUCT_PRICE_INVALID_AMOUNT")
    @Max(value = 10000000, message = "PRODUCT_PRICE_INVALID_AMOUNT")
    Float price;

    String thumbnail;

    String description;

    @Min(value = 1, message = "PRODUCT_CATEGORY_ID_INVALID")
    Long category;
}
