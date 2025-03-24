package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.model.InventoryItem;
import com.example.inventorymanagementsystem.model.ItemCategory;
import com.example.inventorymanagementsystem.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/itemId/{id}")
    public ResponseEntity<Optional<InventoryItem>> getItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(inventoryService.getItemById(id));
    }

    @GetMapping("/categoryId/{id}")
    public ResponseEntity<List<InventoryItem>> getItemByCategoryId(@PathVariable Integer id) {
        return ResponseEntity.ok(inventoryService.getItemByCategoryId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryItem>> getLowStockItems(@RequestParam Integer threshold) {
        return ResponseEntity.ok(inventoryService.getLowStockItems(threshold));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer id) {
        boolean deleted = inventoryService.deleteItemById(id);
        return deleted ? ResponseEntity.ok("[Item deleted successfully]") : ResponseEntity.badRequest().body(">! Item not found");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody InventoryItem item) {
        inventoryService.addItem(item);
        return ResponseEntity.ok("[Item added successfully]");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateItem(@PathVariable int id,
                                             @RequestParam String name,
                                             @RequestParam(required = false) String description,
                                             @RequestParam String categoryName,
                                             @RequestParam(required = false) String categoryDescription,
                                             @RequestParam int quantity,
                                             @RequestParam boolean borrowable,
                                             @RequestParam int dayOfCreation,
                                             @RequestParam int monthOfCreation,
                                             @RequestParam int yearOfCreation) {
        Optional<InventoryItem> item = inventoryService.getItemById(id);

        if(item.isEmpty())
            return ResponseEntity.badRequest().body(">! Item not found");

        InventoryItem itemToUpdate = item.get();

        ItemCategory newCategory = new ItemCategory(categoryName, categoryDescription);

        itemToUpdate.setName(name);
        itemToUpdate.setDescription(description);
        itemToUpdate.setItemCategory(newCategory);
        itemToUpdate.setQuantity(quantity);
        itemToUpdate.setBorrowable(borrowable);
        itemToUpdate.setCreatedAt(yearOfCreation, monthOfCreation, dayOfCreation);

        boolean updated = inventoryService.updateItem(itemToUpdate);
        return updated ? ResponseEntity.ok("[Item updated successfully]") : ResponseEntity.badRequest().body(">! Item was not updated");
    }
}
