package com.najmi.test.MayApp.Service;

import com.najmi.test.MayApp.Models.Product;
import com.najmi.test.MayApp.Repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    // Get all products with pagination
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    // Get product by ID
    public Product getProductById(int productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Add new product
    @Transactional
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    // Update product
    @Transactional
    public Product updateProduct(int productId, Product updatedProduct) {
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());

        return productRepo.save(existingProduct);
    }

    // Delete product
    @Transactional
    public void deleteProduct(int productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepo.delete(product);
    }
}
