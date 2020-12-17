package com.zindabad.ecommerce.controller;

import com.zindabad.ecommerce.model.*;
import com.zindabad.ecommerce.repository.CustomerRepository;
import com.zindabad.ecommerce.repository.OrderRepository;
import com.zindabad.ecommerce.repository.PaymentRepository;
import com.zindabad.ecommerce.repository.ProductRepository;
import com.zindabad.ecommerce.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class PaymentController {

    final
    PaymentRepository paymentRepository;
    final
    OrderRepository orderRepository;
    final
    CustomerRepository customerRepository;

    public PaymentController(PaymentRepository paymentRepository, OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    //Generate Random Alphanumeric String With Java 8..
    public String getRandomNo() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
    //Make payment.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/makePayment/{orderId}", method = RequestMethod.POST)
    public String makePayment(@PathVariable("orderId") long orderId){
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);
        Order order = orderRepository.findById(orderId).get();

        long millis = System.currentTimeMillis();
        Date paymentDate = new Date(millis);

        String paymentReference = getRandomNo();
        Customer customer = order.getCustomer();
        Double amount = order.getTotalPrice();

        payment.setAmount(amount);
        payment.setCustomer(customer);
        payment.setDate(paymentDate);
        payment.setOrder(order);
        payment.setReference(paymentReference);
        payment.setPaid(true);

        paymentRepository.save(payment);
        order.setStatus("Completed");
        orderRepository.save(order);

        return "Payment created successfully.";
    }

    //Get list of all payment.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/payments", method = RequestMethod.GET)
    public List allPayments(){

        return (List) paymentRepository.findAll();
    }

    //Get the details of a payment.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value = "/payment/{id}", method = RequestMethod.GET)
    public Payment paymentDetails(@PathVariable("id") long id){
        Payment payment = paymentRepository.findById(id).get();

        return payment;
    }

    //Get the list of payment by a customer.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/myPayments", method = RequestMethod.GET)
    public List myPayments(Authentication authentication){
        String username = authentication.getName(); //get Signed UserName...
        Customer customer = customerRepository.findCustomerByUsername(username);

        return paymentRepository.findPaymentsByCustomerId(customer.getId());
    }

    //List of payment made by a customer query by admin.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/customerPayments/{id}", method = RequestMethod.GET)
    public List customerPayments(@PathVariable("id") long id){
        Customer customer = customerRepository.findById(id).get();

        return paymentRepository.findPaymentsByCustomerId(customer.getId());
    }

    //Change the status of a payment.
//    @CrossOrigin(exposedHeaders = "http://localhost:8098")
//    @RequestMapping(path = "/updateStatus/{id}", method = RequestMethod.PUT)
//    public String updateStatus(@PathVariable("id") long id, @RequestParam Boolean status){
//        Payment payment = paymentRepository.findById(id).get();
//        payment.setStatus(status);
//
//        return "Status updated successfully.";
//    }

}



