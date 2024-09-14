package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.CouponRequest;
import com.anonymous.shop_application.dtos.responses.CouponResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.mappers.CouponMapper;
import com.anonymous.shop_application.models.Coupon;
import com.anonymous.shop_application.repositories.CouponRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CouponService {

    CouponRepository couponRepository;

    CouponMapper couponMapper;

    public CouponResponse create(CouponRequest request) {

        // End date ket thuc toi thieu 1 tieng sau start date
        validateTimeLimit(request);
        validateDiscount(request);

        if (couponRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.COUPON_CODE_EXISTS);
        }

        Coupon coupon = couponMapper.mapToCoupon(request);
        coupon.setIsActive(1);
        Coupon created = couponRepository.save(coupon);

        return couponMapper.mapToCouponResponse(created);
    }

    public List<CouponResponse> getAll() {
        return couponRepository.findAll()
                .stream()
                .map(couponMapper::mapToCouponResponse)
                .collect(Collectors.toList());
    }

    public String blockOrEnableCoupon(Long id, int active) {
        if (!(0 <= active && active <= 1))
            throw new AppException(ErrorCode.COUPON_ACTIVE_INVALID);

        Coupon coupon = findCouponById(id);

        coupon.setIsActive(active);
        couponRepository.save(coupon);
        return active > 0 ? "Successfully enabled the coupon" : "Successfully blocked the coupon";
    }

    private void validateDiscount(CouponRequest request) {
        boolean hasZeroDiscount = request.getDiscountAmount() == 0F && request.getDiscountPercentage() == 0F;
        boolean hasBothDiscounts = request.getDiscountPercentage() != 0F && request.getDiscountAmount() != 0F;

        if (hasZeroDiscount || hasBothDiscounts) {
            throw new AppException(ErrorCode.COUPON_DISCOUNT_INVALID);
        }
    }

    public Coupon findCouponById(Long id) {
        if (id != null) return couponRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));
        return null;
    }

    private void validateTimeLimit(CouponRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate().plusHours(1))) {
            throw new AppException(ErrorCode.COUPON_DATE_INVALID);
        }
    }

    public Float getCoupon(Float amount, Coupon coupon) {
        if (coupon == null) return amount;
        LocalDateTime currentTime = LocalDateTime.now();
        if (!(coupon.getStartDate().isBefore(currentTime) &&
                coupon.getEndDate().isAfter(currentTime)))
            throw new AppException(ErrorCode.COUPON_EXPIRED, String.valueOf(coupon.getId()));

        Float discountAmount = coupon.getDiscountAmount();
        Float discountPercentage = coupon.getDiscountPercentage();

        if (discountAmount != 0F) {
            amount = (amount >= discountAmount) ? amount - discountAmount : 0F;
        } else if (discountPercentage != 0F) {
            amount -= amount * (discountPercentage / 100);
        }
        return amount;
    }
}
