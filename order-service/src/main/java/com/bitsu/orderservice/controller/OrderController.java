package com.bitsu.orderservice.controller;


import com.bitsu.orderservice.dto.OrderRequest;
import com.bitsu.orderservice.dto.OrderResponse;
import com.bitsu.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Palced";
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id){
        return orderService.getOrder(id);
    }

    @GetMapping
    public List<OrderResponse> getOrder(){
        return orderService.getOrder();
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderService.delteOrder(id);
    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest){
        orderService.updateOrder(id, orderRequest);
    }
}
