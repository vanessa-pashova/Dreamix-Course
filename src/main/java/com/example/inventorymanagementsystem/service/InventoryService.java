package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.InventoryItem;
import com.example.inventorymanagementsystem.repository.InventoryItemRepository;
import com.example.inventorymanagementsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryItemRepository inventoryItemRepository;
//    private final TransactionRepository transactionRepository;

    public InventoryService(InventoryItemRepository inventoryItemRepository, TransactionRepository transactionRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
//        this.transactionRepository = transactionRepository;
    }

    public Optional<InventoryItem> getItemById(int itemId) {
        return inventoryItemRepository.findById(itemId);
    }

    public List<InventoryItem> getItemByCategoryId(int categoryId) {
        return inventoryItemRepository.findByItemCategoryId(categoryId);
    }

    public List<InventoryItem> getAllItems() {
        return inventoryItemRepository.findAll();
    }

    public List<InventoryItem> getLowStockItems(int threshold) {
        return inventoryItemRepository.findAll()
                .stream()
                .filter(item -> item.getQuantity() < threshold)
                .toList();
    }

    public void addItem(InventoryItem item) {
        inventoryItemRepository.save(item);
    }

    public boolean updateItem(InventoryItem item) {
        if(!inventoryItemRepository.existsById(item.getId()))
            return false;

        inventoryItemRepository.save(item);
        return true;
    }

    public boolean deleteItemById(int itemId) {
        if(inventoryItemRepository.existsById(itemId)) {
            inventoryItemRepository.deleteById(itemId);
            return true;
        }

        return false;
    }
}
