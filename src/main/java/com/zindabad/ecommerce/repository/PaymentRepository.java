package com.zindabad.ecommerce.repository;

import com.zindabad.ecommerce.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

   List<Payment> findPaymentsByCustomerId(long id);
   Payment findPaymentByOrderId(long id);

}
