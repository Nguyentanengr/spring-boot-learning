package com.anonymous.shop_application.dtos.requests;

import com.anonymous.shop_application.validators.LimitFileImage;
import com.anonymous.shop_application.validators.MinElement;
import com.anonymous.shop_application.validators.NotEmptyFile;
import com.anonymous.shop_application.validators.ValidImage;
import com.anonymous.shop_application.validators.sequences.FirstOrder;
import com.anonymous.shop_application.validators.sequences.SecondOrder;
import com.anonymous.shop_application.validators.sequences.ThirdOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@GroupSequence({ProductRemoveImageRequest.class, FirstOrder.class, SecondOrder.class})
public class ProductRemoveImageRequest {

    @NotEmpty(message = "PRODUCT_IMAGE_ID_INVALID_EMPTY", groups = FirstOrder.class)
    @MinElement(message = "PRODUCT_IMAGE_ID_INVALID", groups = SecondOrder.class)
    List<Long> imageIds;

}
