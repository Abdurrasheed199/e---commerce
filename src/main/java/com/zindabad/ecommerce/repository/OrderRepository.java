package com.zindabad.ecommerce.repository;

import com.zindabad.ecommerce.model.Order;
import org.springframework.data.repository.CrudRepository;



public interface OrderRepository extends CrudRepository<Order, Long> {
}
