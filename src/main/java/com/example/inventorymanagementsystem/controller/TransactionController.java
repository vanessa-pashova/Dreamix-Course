package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.model.Transaction;
import com.example.inventorymanagementsystem.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable("itemId") int id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Transaction>> getTransactionByClubMemberId(@PathVariable("memberId") int id) {
        return ResponseEntity.ok(transactionService.getTransactionsByClubMemberId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Transaction>> getOverdueTransactions() {
        return ResponseEntity.ok(transactionService.getOverdueTransactions());
    }

    @GetMapping("/borrowedItems")
    public ResponseEntity<List<Object[]>> getBorrowedItems() {
        return ResponseEntity.ok(transactionService.getTopBorrowedItems());
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnItem(@PathVariable int id) {
        boolean result = transactionService.returnItem(id);
        return result ? ResponseEntity.ok("[Item Returned]") : ResponseEntity.badRequest().body(">! Item not found");
    }
}
