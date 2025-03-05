package com.najmi.test.MayApp.Controller;

import com.najmi.test.MayApp.Models.Order;
import com.najmi.test.MayApp.Models.Product;
import com.najmi.test.MayApp.Repo.OrderRepo;
import com.najmi.test.MayApp.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    public static final String ACCOUNT_SID = "//";
    public static final String AUTH_TOKEN = "//";

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    @GetMapping(value = "/")
    public String getPage() {
        return "Hello World";
    }

    @GetMapping(value="/products")
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @GetMapping(value="/orders")
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

    @PostMapping(value = "/add_product")
    public void saveProduct(@RequestBody Product product) {
        productRepo.save(product);
    }

    @PostMapping(value = "/make_order")
    public void makeOrder(@RequestBody Map<String, Object> request) {

        int productId = (int) request.get("product");  // Extract product ID
        String phoneNumber = (String) request.get("phoneNumber");

        // Fetch product from database
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Create order object
        Order order = new Order();
        order.setProduct(product);
        order.setPhoneNumber(phoneNumber);

        // Send SMS via Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("+19896584188"),
                "You have ordered " + product.getName() + " - " + product.getDescription()
        ).create();

        // Save order
        orderRepo.save(order);

    }

}
