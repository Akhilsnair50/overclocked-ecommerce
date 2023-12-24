package com.shopme.checkout;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import com.shopme.common.entity.coupon.Coupon;
import com.shopme.common.entity.order.Order;
import com.shopme.common.entity.order.PaymentMethod;
import com.shopme.coupon.CouponService;
import com.shopme.order.OrderRepository;
import com.shopme.order.OrderService;
import com.shopme.setting.CurrencySettingBag;
import com.shopme.setting.EmailSettingBag;
import com.shopme.setting.PaymentSettingBag;
import com.shopme.setting.SettingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shopme.Utility;
import com.shopme.address.AddressService;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.ShippingRate;
import com.shopme.customer.CustomerService;
import com.shopme.shipping.ShippingRateService;
import com.shopme.shoppingcart.ShoppingCartService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CheckoutController {

    @Autowired private CheckoutService checkoutService;
    @Autowired private CustomerService customerService;
    @Autowired private AddressService addressService;
    @Autowired private ShippingRateService shipService;
    @Autowired private ShoppingCartService cartService;
    @Autowired private OrderService orderService;
    @Autowired private SettingService settingService;
    @Autowired private OrderRepository orderRepo;
    @Autowired private CouponService couponService;
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            model.addAttribute("shippingAddress", defaultAddress.toString());
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            model.addAttribute("shippingAddress", customer.getAddress());
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        if (shippingRate == null) {
            return "redirect:/cart";
        }

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);
        String currencyCode = settingService.getCurrencyCode();
        PaymentSettingBag paymentSettings = settingService.getPaymentSettings();

        model.addAttribute("currencyCode", currencyCode);
        model.addAttribute("customer", customer);

        model.addAttribute("coupon",new Coupon());
        model.addAttribute("checkoutInfo", checkoutInfo);
        model.addAttribute("cartItems", cartItems);

        return "checkout/checkout";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }


    @PostMapping("/place_order")
    public String placeOrder(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String paymentType = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);

        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        Order createdOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkoutInfo);

        cartService.deleteByCustomer(customer);

        sendOrderConfirmationEmail(request, createdOrder);
//        System.out.println(request.getParameterMap().toString());
//        System.out.println(checkoutInfo.getPaymentTotal());
        return "checkout/order_completed";
    }

//    @PostMapping("/verify_order_razorpay")
//    public String verifyRazorPay(@RequestBody Map<String,Object> data,HttpServletRequest request, Model model){
//        String orderId = request.getParameter("orderId");
//return "deone";
//    }


    private void sendOrderConfirmationEmail(HttpServletRequest request, Order order)
            throws UnsupportedEncodingException, MessagingException {
        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
        mailSender.setDefaultEncoding("utf-8");

        String toAddress = order.getCustomer().getEmail();
        String subject = emailSettings.getOrderConfirmationSubject();
        String content = emailSettings.getOrderConfirmationContent();

        subject = subject.replace("[[orderId]]", String.valueOf(order.getId()));

        MimeMessage message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message);
        String senderName = "Order cofirmation";

        helper.setFrom("akhilsnair50@gmail.com", senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        DateFormat dateFormatter =  new SimpleDateFormat("HH:mm:ss E, dd MMM yyyy");
        String orderTime = dateFormatter.format(order.getOrderTime());

        CurrencySettingBag currencySettings = settingService.getCurrencySettings();
        String totalAmount = Utility.formatCurrency(order.getTotal(), currencySettings);

        content = content.replace("[[name]]", order.getCustomer().getFullName());
        content = content.replace("[[orderId]]", String.valueOf(order.getId()));
        content = content.replace("[[orderTime]]", orderTime);
        content = content.replace("[[shippingAddress]]", order.getShippingAddress());
        content = content.replace("[[total]]", totalAmount);
        content = content.replace("[[paymentMethod]]", order.getPaymentMethod().toString());

        helper.setText(content, true);
        mailSender.send(message);
    }


// ----------------------------------Razorpay-------------------------------------------------------------//

//    @RequestMapping(value = "/create_order" ,method = RequestMethod.POST)
@PostMapping("/create_order")
@ResponseBody
    public String create(@RequestBody Map<String,Object> data) throws RazorpayException {
        System.out.println("The order function executed");
        System.out.println(data);

        Float amt = Float.parseFloat(data.get("amount").toString());
        var client =new RazorpayClient("rzp_test_YeGMvUyMRhiqlP","FakJyJyE6bX6AcLCIrxmOCJh");
        JSONObject ob = new JSONObject();
        ob.put("amount",amt);
        ob.put("currency","INR");
//        ob.put("reciept","txn_1213");
//        ob.put("receipt",);
        com.razorpay.Order order = client.orders.create(ob);
        System.out.println(order);

        return order.toString();
    }

    @PostMapping("/apply-coupon")
    public String applyCoupon(@ModelAttribute("coupon") Coupon coupon,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request,
                              Model model) {
        Optional<Coupon> optionalCoupon = couponService.findCouponByCode(coupon.getCode());

//        String checkoutInfo = request.getParameter("checkoutInfo");
        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        String currencyCode = settingService.getCurrencyCode();
        PaymentSettingBag paymentSettings = settingService.getPaymentSettings();

        model.addAttribute("currencyCode", currencyCode);
        model.addAttribute("customer", customer);

        model.addAttribute("coupon",new Coupon());
        model.addAttribute("cartItems", cartItems);
        System.out.println(checkoutInfo.getPaymentTotal());

        if (optionalCoupon.isPresent()) {
            System.out.println("inside the coupon");
            System.out.println(checkoutInfo.getPaymentTotal());

            // Apply discount logic here
            int discountPercentage = optionalCoupon.get().getDiscount();
            System.out.println(discountPercentage);
            float updatedPayment = checkoutInfo.getPaymentTotal()-((discountPercentage*checkoutInfo.getPaymentTotal())/100);

            checkoutInfo.setPaymentTotal(updatedPayment);
            checkoutInfo.setRazorPayTotal(updatedPayment*100);

            System.out.println(updatedPayment);
//            checkoutInfo.setRazorPayTotal(discountPercentage);
            // Implement your discount logic, update the cart, etc.
            redirectAttributes.addFlashAttribute("successMessage", "Coupon applied successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid coupon code!");
        }
        model.addAttribute("checkoutInfo", checkoutInfo);

        return "checkout/checkout";
    }

}