package com.najmi.test.MayApp.Repo;

import com.najmi.test.MayApp.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
