package com.shopme.admin.coupon;

import com.shopme.common.entity.coupon.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CouponController {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired CouponService couponService;
    @GetMapping("/coupons")
    public String listCoupons(Model model) {
        List<Coupon> coupons = couponRepository.findAll();
        model.addAttribute("coupons", coupons);
        return "coupons/coupons";
    }

    @GetMapping("coupons/create")
    public String showCreateCouponForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "coupons/createCoupon";
    }

    @PostMapping("coupons/save")
    public String createCoupon(Coupon coupon,Model model) {
        couponRepository.save(coupon);
        model.addAttribute("message","success");
        return "redirect:/coupons";
    }

    @GetMapping("/edit/{id}")
    public String showEditCouponForm(@PathVariable Integer id, Model model) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        optionalCoupon.ifPresent(coupon -> model.addAttribute("coupon", coupon));
        return "editCoupon";
    }

    @PostMapping("/edit")
    public String editCoupon(@ModelAttribute Coupon coupon) {
        couponRepository.save(coupon);
        return "redirect:/coupons";
    }

    @GetMapping("/coupons/{id}/enabled/{status}")
    public String updateCouponStatus(@PathVariable("id")Integer id, @PathVariable("status")boolean enabled, RedirectAttributes redirectAttributes){
        couponService.couponEnabledStatus(id,enabled);
        String status = enabled? "enabled":"disabled";
        String message = "coupon " + id+" has been "+status;

        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/coupons";
    }

    @GetMapping("/coupons/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            couponService.delete(id);;
            redirectAttributes.addFlashAttribute("message",
                    "The coupon ID " + id + " has been deleted successfully");
        } catch (CouponNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/coupons";
    }
}
