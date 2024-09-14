package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.OrderDetailRequest;
import com.anonymous.shop_application.dtos.requests.OrderRequest;
import com.anonymous.shop_application.dtos.responses.OrderDetailListResponse;
import com.anonymous.shop_application.dtos.responses.OrderDetailResponse;
import com.anonymous.shop_application.dtos.responses.OrderListResponse;
import com.anonymous.shop_application.dtos.responses.OrderResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.mappers.OrderDetailMapper;
import com.anonymous.shop_application.mappers.OrderMapper;
import com.anonymous.shop_application.models.*;
import com.anonymous.shop_application.repositories.OrderDetailRepository;
import com.anonymous.shop_application.repositories.OrderRepository;
import com.anonymous.shop_application.repositories.ProductRepository;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class OrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;
    CouponService couponService;
    UserService userService;
    OrderDetailMapper orderDetailMapper;
    ProductService productService;


    public OrderListResponse getAllOrders(Float amount, Long userId, Pageable pageable) {
        Page<Order> page = orderRepository
                .findAllOrderByFilter(amount, userId, pageable);

        return OrderListResponse.builder()
                .orders(page
                        .map(orderMapper::mapToOrderResponse)
                        .stream().collect(Collectors.toList()))
                .totalPages(page.getTotalPages())
                .build();
    }

    public OrderResponse createOrder(OrderRequest request) {

        // check userid
        User user = userService.findUserById(request.getUserId());

        Order order = orderMapper.mapToOrder(request);


        order.setUser(user);
        order.setActive(1);
        order.setOrderDate(LocalDateTime.now());
//        order.setStatus(Status.PROCESSING);
        order.setTrackingNumber(Faker.instance().numerify("##########"));
        order.setTotalMoney(calculateTotalMoney(request));
        order.setShippingDate(LocalDate.now().plusDays(5));
        Set<OrderDetailRequest> orderDetailRequests= request.getOrderDetails();
        Set<OrderDetail> orderDetails = new HashSet<>();

        for (OrderDetailRequest odr : orderDetailRequests) {
            Product product = productService.findProductById(odr.getProductId());
            Coupon coupon = couponService.findCouponById(odr.getCouponId());

            OrderDetail orderDetail = orderDetailMapper.mapToOrderDetail(odr);
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setCoupon(coupon);

            orderDetails.add(orderDetail);
        }

        Long couponId = request.getCouponId();
        order.setCoupon(couponService.findCouponById(couponId));

        order.setOrderDetails(orderDetails);
        return orderMapper.mapToOrderResponse(orderRepository.save(order));
    }

    public OrderListResponse getOrdersByUserId(Long userId, Pageable pageable) {
        userService.getUserById(userId);

        Page<Order> page = orderRepository.findOrderByUserId(userId, pageable);
        return OrderListResponse.builder()
                .orders(page
                        .map(orderMapper::mapToOrderResponse)
                        .stream().collect(Collectors.toList())
                )
                .totalPages(page.getTotalPages())
                .build();
    }

    public String deleteOrder(Long id) {
        Order order = getByOrderId(id);

        order.setActive(0);
        orderRepository.save(order);

        return "Order have been deleted";
    }

    public Order getByOrderId(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }


    private Float calculateTotalMoney(OrderRequest request) {
        Float totalMoneyPerOrder = 0F;

        Set<OrderDetailRequest> orderDetailRequests = request.getOrderDetails();
        for (OrderDetailRequest orderDetailRequest : orderDetailRequests) {
            Long couponId = orderDetailRequest.getCouponId();
            Coupon coupon = couponService.findCouponById(couponId);
            totalMoneyPerOrder += couponService.getCoupon(orderDetailRequest.getTotalMoney(), coupon);
        }

        Long couponId = request.getCouponId();

        Coupon coupon = couponService.findCouponById(couponId);
        return couponService.getCoupon(totalMoneyPerOrder, coupon);
    }




//    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest request) {
//
//        OrderDetail orderDetail = findOrderDetailById(id);
//
//        orderDetailMapper.updateToOrderDetail(request, orderDetail);
//
//        Product product = productRepository.findById(request.getProductId())
//                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
//
//        Coupon coupon = null;
//        if (request.getCouponId() != null)
//            coupon = couponService.findCouponById(request.getCouponId());
//
//        orderDetail.setProduct(product);
//        orderDetail.setCoupon(coupon);
//        return orderDetailMapper.mapToOrderDetailResponse(orderDetailRepository.save(orderDetail));
//    }
//
//    public void deleteOrderDetail(Long id) {
//        OrderDetail orderDetail = findOrderDetailById(id);
//        orderDetailRepository.delete(orderDetail);
//    }
//
//    public OrderDetail findOrderDetailById(Long id) {
//        return orderDetailRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
//
//    }


}
