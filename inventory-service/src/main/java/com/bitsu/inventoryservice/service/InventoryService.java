package com.bitsu.inventoryservice.service;

import com.bitsu.inventoryservice.dto.InventoryResponse;
import com.bitsu.inventoryservice.model.Inventory;
import com.bitsu.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCodes){
        var inventory = inventoryRepository.findBySkuCodeIn(skuCodes);
        log.info("Stock Check: {}", inventory);

        return inventory.stream()
                .map(this::mapToInventoryResponse)
                .toList();
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory){
        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build();
    }
}
