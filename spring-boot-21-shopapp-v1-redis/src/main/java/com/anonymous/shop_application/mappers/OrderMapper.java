package com.anonymous.shop_application.mappers;

import com.anonymous.shop_application.dtos.requests.OrderRequest;
import com.anonymous.shop_application.dtos.responses.OrderResponse;
import com.anonymous.shop_application.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface OrderMapper {


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "coupon", ignore = true)
    Order mapToOrder(OrderRequest request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "coupon", ignore = true)
    Order updateToOrder(OrderRequest request, @MappingTarget Order order);

    OrderResponse mapToOrderResponse(Order order);

}
