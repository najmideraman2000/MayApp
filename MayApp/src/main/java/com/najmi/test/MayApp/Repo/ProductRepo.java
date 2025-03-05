package com.najmi.test.MayApp.Repo;

import com.najmi.test.MayApp.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
