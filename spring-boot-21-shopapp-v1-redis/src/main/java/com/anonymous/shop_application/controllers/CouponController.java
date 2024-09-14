package com.anonymous.shop_application.controllers;

import com.anonymous.shop_application.dtos.requests.CouponRequest;
import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.dtos.responses.CouponResponse;
import com.anonymous.shop_application.services.CouponService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/coupons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CouponController {

    CouponService couponService;

    @PostMapping
    public ApiResponse<CouponResponse> createCoupon(@RequestBody @Valid CouponRequest request) {
        return ApiResponse.<CouponResponse>builder()
                .value(couponService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CouponResponse>> getAllCoupons() {
        return ApiResponse.<List<CouponResponse>>builder()
                .value(couponService.getAll())
                .build();
    }

    @DeleteMapping("/{id}/{active}")
    public ApiResponse<String> blockOrEnableCoupon(
            @PathVariable("id") Long id,
            @PathVariable("active") int active) {
        return ApiResponse.<String>builder()
                .value(couponService.blockOrEnableCoupon(id, active))
                .build();
    }
}
