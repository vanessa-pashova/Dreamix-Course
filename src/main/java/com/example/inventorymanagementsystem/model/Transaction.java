package com.example.inventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name = "transaction")
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private ClubMember clubMember;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private InventoryItem item;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int quantityUsed;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column
    private LocalDate returnDate;

    //Initial Constructor
    public Transaction(ClubMember clubMember, InventoryItem inventoryItem, String type, int quantityUsed, int borrowYear, int borrowMonth, int borrowDay, int returnYear, int returnMonth, int returnDay) {
        this.setClubMember(clubMember);
        this.setItem(inventoryItem);
        this.setType(type);
        this.setQuantityUsed(quantityUsed);
        this.setBorrowDate(borrowYear, borrowMonth, borrowDay);
        this.setReturnDate(returnYear, returnMonth, returnDay);
    }

    //If borrow = false Constructor
    public Transaction(ClubMember clubMember, InventoryItem inventoryItem, String type, int quantityUsed) {
        this.setClubMember(clubMember);
        this.setItem(inventoryItem);
        this.setType(type);
        this.setQuantityUsed(quantityUsed);
    }

    public void setClubMember(ClubMember clubMember) {
        if(clubMember == null)
            throw new IllegalArgumentException(">! Club member cannot be null");

        this.clubMember = clubMember;
    }

    public void setItem(InventoryItem item) {
        if(item == null)
            throw new IllegalArgumentException(">! Inventory item cannot be null");

        this.item = item;
    }

    public void setType(String type) {
        if (type == null || (!type.equalsIgnoreCase("borrow") && !type.equalsIgnoreCase("use")))
            throw new IllegalArgumentException(">! Invalid type, must be [borrow|use]");

        this.type = type.toLowerCase();
    }

    public void setQuantityUsed(int quantityUsed) {
        if(quantityUsed < 0)
            throw new IllegalArgumentException(">! The used quantity cannot be negative");

        this.quantityUsed = quantityUsed;
    }

    private void validateDate(int year, int month, int day) {
        if (year < 2022 || year > LocalDate.now().getYear())
            throw new IllegalArgumentException(">! Year must be between 2022 and the current year.");

        if (month < 1 || month > 12)
            throw new IllegalArgumentException(">! Invalid month, must be [1,12]");

        YearMonth yearMonth = YearMonth.of(year, month);
        int maxDays = yearMonth.lengthOfMonth();

        if (day < 1 || day > maxDays)
            throw new IllegalArgumentException(">! Invalid day for the given month.");
    }

    public void setBorrowDate(int year, int month, int day) {
        validateDate(year, month, day);
        this.borrowDate = LocalDate.of(year, month, day);
    }

    public void setReturnDate(int year, int month, int day) {
        validateDate(year, month, day);
        this.returnDate = LocalDate.of(year, month, day);

        if (this.borrowDate != null && this.returnDate.isBefore(this.borrowDate))
            throw new IllegalArgumentException(">! Return date cannot be before the borrow date");
    }

    public void setReturnDate() {
        this.returnDate = LocalDate.now();
    }
}
