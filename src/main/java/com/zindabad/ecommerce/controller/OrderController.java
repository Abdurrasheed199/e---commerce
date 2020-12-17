package com.zindabad.ecommerce.controller;

import com.zindabad.ecommerce.model.*;
import com.zindabad.ecommerce.repository.*;
import org.springframework.web.bind.annotation.*;



import java.util.Date;
import java.util.List;

@RestController
public class OrderController {

    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final OrderProductRepository orderProductRepository;
    final PaymentRepository paymentRepository;
    final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository, OrderProductRepository orderProductRepository, PaymentRepository paymentRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderProductRepository = orderProductRepository;
        this.paymentRepository = paymentRepository;
        this.productRepository = productRepository;
    }

    //Create order
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value = "/order/checkout", method = RequestMethod.POST)
    public String createOrder(@RequestParam long customerId, @RequestParam List<CheckoutOrder> checkoutOrders, @RequestParam String shippingAddress){

        Payment payment = new Payment();
        payment.setPaid(false);
        paymentRepository.save(payment);    //Create New payment and save it...

        Order order = new Order();
        long millis = System.currentTimeMillis();
        Date orderDate = new Date(millis);      // Get current time of order...
        Customer customer = customerRepository.findById(customerId).get();
        order.setCustomer(customer);
        order.setDate(orderDate);
        order.setShippingAddress(shippingAddress);
        order.setPayment(payment);
        order.setStatus("Pending");
        orderRepository.save(order);

        //Get total price from order_products
        Double totalPrice = 0.0D;
        for(CheckoutOrder co : checkoutOrders){
            int quantity = co.quantity;
            long productId = co.productId;
            Product product = productRepository.findById(productId).get();
            OrderProduct orderProduct = new OrderProduct(product, product.getPrice(), order, quantity);
            orderProductRepository.save(orderProduct);
            Double subTotal = product.getPrice() * quantity;
            totalPrice += subTotal;
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return "Order successfully created";
    }

    //Get order by id..
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value="/order/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable("id") long id) {
        Order order = orderRepository.findById(id).get();
        return order;
    }

    //Get the list of all orders...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path="/order/list", method = RequestMethod.GET)
    public List getOrder() {
        return (List)orderRepository.findAll();
    }

    //Update Order Shipping Address
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/order/update", method = RequestMethod.PUT)
    public String update(@RequestParam long id, @RequestParam String shippingAddress) {
        Order order = orderRepository.findById(id).get();
        order.setShippingAddress(shippingAddress);
        orderRepository.save(order);

        return "Shipping address updated successfully.";
    }

    //Delete order...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/order/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") long id) {
        Order order = orderRepository.findById(id).get();
        orderRepository.delete(order);

        return "Order deleted successfully.";
    }

}
