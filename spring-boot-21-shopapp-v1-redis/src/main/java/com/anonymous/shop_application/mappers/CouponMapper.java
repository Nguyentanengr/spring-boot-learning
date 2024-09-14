package com.anonymous.shop_application.mappers;

import com.anonymous.shop_application.dtos.requests.CouponRequest;
import com.anonymous.shop_application.dtos.responses.CouponResponse;
import com.anonymous.shop_application.models.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CouponMapper {

    Coupon mapToCoupon(CouponRequest request);

    Coupon updateToCoupon(CouponRequest request, @MappingTarget Coupon coupon);

    CouponResponse mapToCouponResponse(Coupon coupon);
}
