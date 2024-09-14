package com.anonymous.shop_application.controllers;

import com.anonymous.shop_application.dtos.requests.OrderDetailRequest;
import com.anonymous.shop_application.dtos.requests.OrderRequest;
import com.anonymous.shop_application.dtos.responses.*;
import com.anonymous.shop_application.services.OrderDetailService;
import com.anonymous.shop_application.services.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    OrderService orderService;

    @GetMapping
    public ApiResponse<OrderListResponse> getAllOrders(
            @RequestParam(defaultValue = "0") Float amount,
            @RequestParam(defaultValue = "0") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return ApiResponse.<OrderListResponse>builder()
                .value(orderService.getAllOrders(amount, userId, pageRequest))
                .build();
    }

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .value(orderService.createOrder(request))
                .build();
    }

    @GetMapping("user/{userId}")
    public ApiResponse<OrderListResponse> getOrdersByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit)
    {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return ApiResponse.<OrderListResponse>builder()
                .value(orderService.getOrdersByUserId(userId, pageRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteOrder(@PathVariable("id") Long id) {
        return ApiResponse.<String>builder()
                .value(orderService.deleteOrder(id))
                .build();
    }
}
