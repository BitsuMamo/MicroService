package com.bitsu.orderservice.service;

import com.bitsu.orderservice.dto.InventoryResponse;
import com.bitsu.orderservice.dto.OrderLineItemDto;
import com.bitsu.orderservice.dto.OrderRequest;
import com.bitsu.orderservice.dto.OrderResponse;
import com.bitsu.orderservice.model.Order;
import com.bitsu.orderservice.model.OrderLineItem;
import com.bitsu.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
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

        var skuCodes = order.getOrderLineItemsList().stream()
                        .map(OrderLineItem::getSkuCode)
                        .toList();

        InventoryResponse[] inventoryResponse = webClient.get()
                .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponse).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock)
            orderRepository.save(order);
        else
            throw new RuntimeException("Some Products are Not in stock");
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
