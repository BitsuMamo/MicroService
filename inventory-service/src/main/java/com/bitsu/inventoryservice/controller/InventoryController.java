package com.bitsu.inventoryservice.controller;

import com.bitsu.inventoryservice.dto.InventoryResponse;
import com.bitsu.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping
   @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        System.out.println(skuCode);
        return inventoryService.isInStock(skuCode);
    }
}
