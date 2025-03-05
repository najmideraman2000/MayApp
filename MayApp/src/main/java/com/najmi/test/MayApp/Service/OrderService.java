package com.najmi.test.MayApp.Service;

import com.najmi.test.MayApp.Models.Order;
import com.najmi.test.MayApp.Models.Product;
import com.najmi.test.MayApp.Repo.OrderRepo;
import com.najmi.test.MayApp.Repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Map;

@Service
public class OrderService {

    public static final String ACCOUNT_SID = "//";  // Replace with actual Twilio Account SID
    public static final String AUTH_TOKEN = "//";   // Replace with actual Twilio Auth Token
    public static final String TWILIO_PHONE_NUMBER = "+19896584188";  // Replace with Twilio phone number

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    // Get all orders with pagination
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    // Get order by ID
    public Order getOrderById(int orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Make a new order with Twilio SMS
    @Transactional
    public void makeOrder(Map<String, Object> request) {
        int productId = (int) request.get("product");
        String phoneNumber = (String) request.get("phoneNumber");

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Order order = new Order();
        order.setProduct(product);
        order.setPhoneNumber(phoneNumber);
        orderRepo.save(order);

        sendSms(phoneNumber, "Your order for " + product.getName() + " has been placed successfully.");
    }

    // Update order with Twilio SMS
    @Transactional
    public Order updateOrder(int orderId, int productId, String phoneNumber) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.setProduct(product);
        order.setPhoneNumber(phoneNumber);
        orderRepo.save(order);

        sendSms(phoneNumber, "Your order has been updated to: " + product.getName());

        return order;
    }

    // Delete order with Twilio SMS
    @Transactional
    public void deleteOrder(int orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String phoneNumber = order.getPhoneNumber();
        orderRepo.delete(order);

        sendSms(phoneNumber, "Your order has been cancelled.");
    }

    // Twilio SMS Helper Method
    private void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(TWILIO_PHONE_NUMBER),
                    messageBody
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send SMS: " + e.getMessage());
        }
    }
}
