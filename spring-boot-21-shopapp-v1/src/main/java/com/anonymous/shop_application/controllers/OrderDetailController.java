package com.anonymous.shop_application.controllers;

import com.anonymous.shop_application.dtos.requests.CouponRequest;
import com.anonymous.shop_application.dtos.requests.OrderDetailRequest;
import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.dtos.responses.CouponResponse;
import com.anonymous.shop_application.dtos.responses.OrderDetailListResponse;
import com.anonymous.shop_application.dtos.responses.OrderDetailResponse;
import com.anonymous.shop_application.services.CouponService;
import com.anonymous.shop_application.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderDetailController {

    OrderDetailService orderDetailService;

    @GetMapping
    public ApiResponse<OrderDetailListResponse> getAllOrderDetails(
            @RequestParam(defaultValue = "0") Float amount,
            @RequestParam(defaultValue = "0") Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return ApiResponse.<OrderDetailListResponse>builder()
                .value(orderDetailService.getAllOrderDetails(amount, orderId, pageRequest))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderDetailResponse> updateOrderDetail(
            @PathVariable("id") Long id,
            @RequestBody OrderDetailRequest request)
    {
        return ApiResponse.<OrderDetailResponse>builder()
                .value(orderDetailService.updateOrderDetail(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteOrderDetail(@PathVariable("id") Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
