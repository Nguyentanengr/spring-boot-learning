package com.anonymous.shop_application.mappers;

import com.anonymous.shop_application.dtos.requests.OrderDetailRequest;
import com.anonymous.shop_application.dtos.responses.OrderDetailResponse;
import com.anonymous.shop_application.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface OrderDetailMapper {


    @Mapping(target = "product", ignore = true)
    @Mapping(target = "coupon", ignore = true)
    OrderDetail mapToOrderDetail(OrderDetailRequest request);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "coupon", ignore = true)
    OrderDetail updateToOrderDetail(OrderDetailRequest request, @MappingTarget OrderDetail orderDetail);

    OrderDetailResponse mapToOrderDetailResponse(OrderDetail orderDetail);

}
