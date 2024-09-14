package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.OrderDetailRequest;
import com.anonymous.shop_application.dtos.responses.OrderDetailListResponse;
import com.anonymous.shop_application.dtos.responses.OrderDetailResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.mappers.OrderDetailMapper;
import com.anonymous.shop_application.models.Coupon;
import com.anonymous.shop_application.models.OrderDetail;
import com.anonymous.shop_application.models.Product;
import com.anonymous.shop_application.repositories.OrderDetailRepository;
import com.anonymous.shop_application.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class OrderDetailService {

    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;
    ProductRepository productRepository;
    CouponService couponService;

    public OrderDetailListResponse getAllOrderDetails(Float amount, Long orderId, Pageable pageable) {
        Page<OrderDetail> page = orderDetailRepository
                .findAllOrderDetailByFilter(amount, orderId, pageable);

        return OrderDetailListResponse.builder()
                .orderDetails(page
                        .map(orderDetailMapper::mapToOrderDetailResponse)
                        .stream().collect(Collectors.toList()))
                .totalPages(page.getTotalPages())
                .build();
    }

    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest request) {

        OrderDetail orderDetail = findOrderDetailById(id);

        orderDetailMapper.updateToOrderDetail(request, orderDetail);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Coupon coupon = null;
        if (request.getCouponId() != null)
            coupon = couponService.findCouponById(request.getCouponId());

        orderDetail.setProduct(product);
        orderDetail.setCoupon(coupon);
        return orderDetailMapper.mapToOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = findOrderDetailById(id);
        orderDetailRepository.delete(orderDetail);
    }

    public OrderDetail findOrderDetailById(Long id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

    }


}
