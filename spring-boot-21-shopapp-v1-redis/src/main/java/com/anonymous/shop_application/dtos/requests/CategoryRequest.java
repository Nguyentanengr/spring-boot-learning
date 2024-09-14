package com.anonymous.shop_application.dtos.requests;

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
public class CategoryRequest {

    @NotBlank(message = "CATEGORY_NAME_INVALID_BLANK")
    @Size(min = 3, max = 200, message = "CATEGORY_NAME_INVALID_SIZE")
    String name;
}
