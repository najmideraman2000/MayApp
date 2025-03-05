package com.najmi.test.MayApp.Controller;

import com.najmi.test.MayApp.Models.Order;
import com.najmi.test.MayApp.Models.Product;
import com.najmi.test.MayApp.Repo.OrderRepo;
import com.najmi.test.MayApp.Service.OrderService;
import com.najmi.test.MayApp.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.najmi.test.MayApp.Repo.ProductRepo;

import java.util.Map;

@RestController
public class APIController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    // ORDER ENDPOINTS

    @PostMapping(value = "/make_order")
    public void makeOrder(@RequestBody Map<String, Object> request) {
        orderService.makeOrder(request);
    }

    @GetMapping(value = "/orders")
    public Page<Order> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderService.getAllOrders(pageable);
    }

    @GetMapping(value = "/order/{id}")
    public Order getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @PutMapping(value = "/update_order/{id}")
    public Order updateOrder(@PathVariable int id, @RequestBody Map<String, Object> request) {
        int productId = (int) request.get("product");
        String phoneNumber = (String) request.get("phoneNumber");

        return orderService.updateOrder(id, productId, phoneNumber);
    }

    @DeleteMapping(value = "/delete_order/{id}")
    public String deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return "Order deleted successfully";
    }

    // PRODUCT ENDPOINTS

    @GetMapping(value = "/products")
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }

    @GetMapping(value = "/product/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @PostMapping(value = "/add_product")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping(value = "/update_product/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping(value = "/delete_product/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
