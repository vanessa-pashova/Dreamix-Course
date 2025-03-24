package com.example.inventorymanagementsystem.repository;

import com.example.inventorymanagementsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findById(int itemId);
    List<Transaction> findByClubMemberId(int clubMemberId);

    @Query("SELECT t.item.name, COUNT(t) FROM Transaction t WHERE t.type = 'borrow' GROUP BY t.item ORDER BY COUNT(t) DESC")
    List<Object[]> getTopBorrowedItems();

    @Query("SELECT t FROM Transaction t WHERE t.returnDate IS NULL AND t.borrowDate < :dueDate")
    List<Transaction> getOverdueTransactions(LocalDate dueDate);
}