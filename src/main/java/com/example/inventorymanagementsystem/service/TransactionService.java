package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.Transaction;
import com.example.inventorymanagementsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<Transaction> getTransactionById(int itemId) {
        return transactionRepository.findById(itemId);
    }

    public List<Transaction> getTransactionsByClubMemberId(int clubMemberId) {
        return transactionRepository.findByClubMemberId(clubMemberId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getOverdueTransactions() {
        return transactionRepository.getOverdueTransactions(LocalDate.now().minusDays(14));
    }

    public boolean returnItem(int itemId) {
        Optional<Transaction> transactions = transactionRepository.findById(itemId);

        if(transactions.isEmpty())
            return false;

        Transaction target = transactions.get();
        if(target.getReturnDate() != null)
            return false;

        target.setReturnDate();
        transactionRepository.save(target);
        return true;
    }

    public List<Object[]> getTopBorrowedItems() {
        return transactionRepository.getTopBorrowedItems();
    }
}
