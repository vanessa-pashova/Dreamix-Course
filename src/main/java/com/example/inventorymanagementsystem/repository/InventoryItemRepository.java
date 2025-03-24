package com.example.inventorymanagementsystem.repository;

import com.example.inventorymanagementsystem.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    List<InventoryItem> findByItemCategoryId(int categoryId);
}
