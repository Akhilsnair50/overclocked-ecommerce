package com.shopme.admin.coupon;

import com.shopme.common.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    Optional<Coupon> findByCode(String code);

    public Long countById(Integer id);

    @Query("update Coupon c set c.enabled = ?2 where c.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id,boolean enabled);

}
