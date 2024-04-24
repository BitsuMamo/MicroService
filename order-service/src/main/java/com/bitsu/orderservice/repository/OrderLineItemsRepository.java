package com.bitsu.orderservice.repository;

import com.bitsu.orderservice.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemsRepository extends JpaRepository<OrderLineItem, Long> {
}
