package com.bitsu.orderservice.dto;

import com.bitsu.orderservice.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderResponse {

    private Long id;
    private String orderNumber;

    private List<OrderLineItemDto> orderLineItemsListDto;

}
