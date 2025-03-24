package com.example.inventorymanagementsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class TransactionTest {

    private ClubMember clubMember;
    private InventoryItem item;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        clubMember = new ClubMember("John Doe", "john.doe@gmail.com");
        item = new InventoryItem("Laptop", "A high-end gaming laptop.", new ItemCategory("Electronics", "Gadgets and devices."), 10, "kg", true, 2024, 1, 18);
        transaction = new Transaction(clubMember, item, "use", 2, 2023, 1, 10, 2023, 1, 20);
    }

    @Test
    public void testConstructor_WithReturnDate_Valid() {
        assertThat(transaction.getClubMember()).isEqualTo(clubMember);
        assertThat(transaction.getItem()).isEqualTo(item);
        assertThat(transaction.getType()).isEqualTo("use");
        assertThat(transaction.getQuantityUsed()).isEqualTo(2);
        assertThat(transaction.getBorrowDate()).isEqualTo(LocalDate.of(2023, 1, 10));
        assertThat(transaction.getReturnDate()).isEqualTo(LocalDate.of(2023, 1, 20));
    }

    @Test
    public void testConstructor_WithoutReturnDate_Valid() {
        Transaction transactionWithoutReturn = new Transaction(clubMember, item, "use", 5);
        assertThat(transactionWithoutReturn.getClubMember()).isEqualTo(clubMember);
        assertThat(transactionWithoutReturn.getItem()).isEqualTo(item);
        assertThat(transactionWithoutReturn.getType()).isEqualTo("use");
        assertThat(transactionWithoutReturn.getQuantityUsed()).isEqualTo(5);
        assertThat(transactionWithoutReturn.getReturnDate()).isNull();
    }

    @Test
    public void testSetType_ValidValues() {
        transaction.setType("borrow");
        assertThat(transaction.getType()).isEqualTo("borrow");

        transaction.setType("use");
        assertThat(transaction.getType()).isEqualTo("use");
    }

    @Test
    public void testSetType_InvalidValues_ShouldThrowException() {
        assertThatThrownBy(() -> transaction.setType("rent"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Invalid type, must be [borrow|use]");
    }

    @Test
    public void testSetQuantityUsed_Valid() {
        transaction.setQuantityUsed(3);
        assertThat(transaction.getQuantityUsed()).isEqualTo(3);
    }

    @Test
    public void testSetQuantityUsed_Invalid_ShouldThrowException() {
        assertThatThrownBy(() -> transaction.setQuantityUsed(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! The used quantity cannot be negative");
    }

    @Test
    public void testSetBorrowDate_Valid() {
        transaction.setBorrowDate(2024, 5, 15);
        assertThat(transaction.getBorrowDate()).isEqualTo(LocalDate.of(2024, 5, 15));
    }

    @Test
    public void testSetBorrowDate_InvalidYear_ShouldThrowException() {
        assertThatThrownBy(() -> transaction.setBorrowDate(2021, 5, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Year must be between 2022 and the current year.");
    }

    @Test
    public void testSetBorrowDate_InvalidDay_ShouldThrowException() {
        assertThatThrownBy(() -> transaction.setBorrowDate(2024, 2, 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Invalid day for the given month.");
    }

    @Test
    public void testSetReturnDate_Valid() {
        transaction.setReturnDate(2024, 4, 15);
        assertThat(transaction.getReturnDate()).isEqualTo(LocalDate.of(2024, 4, 15));
    }

    @Test
    public void testSetReturnDate_BeforeBorrowDate_ShouldThrowException() {
        assertThatThrownBy(() -> transaction.setReturnDate(2022, 3, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Return date cannot be before the borrow date");
    }

    @Test
    public void testSetReturnDate_InvalidLeapYear_ShouldThrowException() {
        assertThatThrownBy(() -> transaction.setReturnDate(2023, 2, 29))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Invalid day for the given month.");
    }
}