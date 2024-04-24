package com.bitsu.orderservice.service;

import com.bitsu.orderservice.dto.OrderLineItemDto;
import com.bitsu.orderservice.dto.OrderRequest;
import com.bitsu.orderservice.dto.OrderResponse;
import com.bitsu.orderservice.model.Order;
import com.bitsu.orderservice.model.OrderLineItem;
import com.bitsu.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(
                        orderRequest.getOrderLineItemsDtoList().stream()
                                .map(this::mapToOrderLineItem)
                                .toList()
                )
                .build();

        order.getOrderLineItemsList()
                .forEach(orderLineItem -> {
                    orderLineItem.setOrder(order);
                });

        orderRepository.save(order);
    }

    public OrderResponse getOrder(Long id) {

        return mapToOrderResponse(getOrderById(id));

    }

    public void delteOrder(Long id){
        Order order = getOrderById(id);

        orderRepository.delete(order);
        log.info("Deleted {}", order);

    }

    public void updateOrder(Long id, OrderRequest orderRequest){
        Order order = getOrderById(id);
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToOrderLineItem).toList());

        orderRepository.save(order);
    }

    private Order getOrderById(Long id){
        var order = orderRepository.findById(id);

        if(order.isEmpty()) throw new NoSuchElementException(String.format("Order with ID: %s Not Found", id));

        return order.get();
    }
    private OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto){
        return OrderLineItem.builder()
                .price(orderLineItemDto.getPrice())
                .skuCode(orderLineItemDto.getSkuCode())
                .quantity(orderLineItemDto.getQuantity())
                .build();
    }

    private OrderLineItemDto mapToOrderLineItemDto(OrderLineItem orderLineItem){
        return OrderLineItemDto.builder()
                .price(orderLineItem.getPrice())
                .skuCode(orderLineItem.getSkuCode())
                .quantity(orderLineItem.getQuantity())
                .build();
    }

    private OrderResponse mapToOrderResponse(Order order){
        return OrderResponse.builder()
                .orderLineItemsListDto(
                        order.getOrderLineItemsList().stream()
                                .map(this::mapToOrderLineItemDto)
                                .toList()
                )
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .build();
    }

    public List<OrderResponse> getOrder() {
        return orderRepository.findAll().stream().map(this::mapToOrderResponse).toList();
    }
}
