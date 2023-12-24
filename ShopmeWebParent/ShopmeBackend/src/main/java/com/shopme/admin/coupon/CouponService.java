package com.shopme.admin.coupon;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CouponService {
    @Autowired CouponRepository couponRepository;
    public void couponEnabledStatus(Integer id , boolean enabled){
        couponRepository.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws CouponNotFoundException {
        Long countById = couponRepository.countById(id);
        if (countById == null || countById == 0) {
//            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        couponRepository.deleteById(id);
    }
}
