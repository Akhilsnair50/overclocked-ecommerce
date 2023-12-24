//package com.shopme.coupon;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/coupons")
//public class CouponController {
//
//    @Autowired
//    private CouponService couponService;
//
//    @PostMapping("/apply")
//    public ResponseEntity<String> applyCoupon(@RequestParam String code) {
//        System.out.println("in the method");
//        if (couponService.applyCoupon(code)) {
//            return ResponseEntity.ok("Coupon applied successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid or expired coupon code");
//        }
//    }
//}