package com.zindabad.ecommerce.controller;

import com.zindabad.ecommerce.model.OrderProduct;
import com.zindabad.ecommerce.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderProductController {

    final
    OrderProductRepository orderProductRepository;

    public OrderProductController(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @CrossOrigin(exposedHeaders = "http://localhost:8896")
 @RequestMapping(path ="/orderProducts", method = RequestMethod.GET)

    public List allOrderProducts(){

     return (List) orderProductRepository.findAll();

 }

 @CrossOrigin(exposedHeaders = "http://localhost:8896")
 @RequestMapping(value = "/orderProduct/{id}",method = RequestMethod.GET)

    public OrderProduct orderProductDetails(@PathVariable("id") long id){
        OrderProduct orderProduct = orderProductRepository.findById(id).get();
        return orderProduct;
 }

}
