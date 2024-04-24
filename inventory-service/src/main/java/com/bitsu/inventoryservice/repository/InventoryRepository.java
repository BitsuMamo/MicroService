package com.bitsu.inventoryservice.repository;

import com.bitsu.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySkuCodeAndQuantityGreaterThan(String skuCode, Integer quantity);

}
