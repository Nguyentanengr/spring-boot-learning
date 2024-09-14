package com.anonymous.shop_application.dtos.requests;

import com.anonymous.shop_application.validators.LimitFileImage;
import com.anonymous.shop_application.validators.NotEmptyFile;
import com.anonymous.shop_application.validators.ValidImage;
import com.anonymous.shop_application.validators.sequences.FirstOrder;
import com.anonymous.shop_application.validators.sequences.SecondOrder;
import com.anonymous.shop_application.validators.sequences.ThirdOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
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
@GroupSequence({ProductUploadImageRequest.class, FirstOrder.class, SecondOrder.class, ThirdOrder.class})
public class ProductUploadImageRequest {

    @NotEmptyFile(message = "PRODUCT_IMAGE_FILE_INVALID_EMPTY", groups = FirstOrder.class)
    @LimitFileImage(max = 5, message = "PRODUCT_IMAGE_FILE_INVALID_SIZE", groups = SecondOrder.class)
    @ValidImage(contentTypes = {"image/"}, message = "PRODUCT_IMAGE_FILE_INVALID_QUALIFIED", groups = ThirdOrder.class)
    List<MultipartFile> files;

}
