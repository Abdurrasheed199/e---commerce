package com.zindabad.ecommerce.repository;

import com.zindabad.ecommerce.model.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
}
