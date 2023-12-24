package com.shopme.coupon;

import com.shopme.checkout.CheckoutInfo;
import com.shopme.common.entity.coupon.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Optional<Coupon> findCouponByCode(String code) {
        return couponRepository.findByCode(code);
    }



//    public boolean applyCoupon(String code) {
//        Optional<Coupon> optionalCoupon = findCouponByCode(code);
//
//        if (optionalCoupon.isPresent()) {
//            Coupon foundCoupon = optionalCoupon.get();
//
//            // Check if the coupon is enabled
//            if (!foundCoupon.isEnabled()) {
//                return false; // Coupon has already been used
//            }
//
//            // Check if the coupon has expired
//            if (foundCoupon.getExpirationDate().isBefore(LocalDate.now())) {
//                return false; // Coupon has expired
//            }
//            CheckoutInfo checkoutInfo = new CheckoutInfo();
//            float updatedPrice = foundCoupon.getDiscount()*checkoutInfo.getPaymentTotal()/100;
//
//            checkoutInfo.setRazorPayTotal(updatedPrice);
//            // Apply the coupon (you can customize this part based on your application logic)
//            // For example, update the order total with the discount
//
//            // Disable the coupon after use
//            foundCoupon.setEnabled(false);
//            couponRepository.save(foundCoupon);
//
//            return true; // Coupon applied successfully
//        }
//
//        return false; // Invalid coupon code
//    }
}
