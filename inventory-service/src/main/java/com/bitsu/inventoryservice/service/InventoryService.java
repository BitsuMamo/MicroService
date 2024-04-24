package com.bitsu.inventoryservice.service;

import com.bitsu.inventoryservice.model.Inventory;
import com.bitsu.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode){
        var inventory = inventoryRepository.findBySkuCodeAndQuantityGreaterThan(skuCode, 0);
        return inventory.isPresent();
    }
}
