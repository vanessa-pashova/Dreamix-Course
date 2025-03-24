package com.example.inventorymanagementsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryItemTest {
    private InventoryItem inventoryItem_Initial, inventoryItem_SecondConstructor;
    private ItemCategory itemCategory;

    @BeforeEach
    public void setUp() {
        this.inventoryItem_Initial = new InventoryItem("MacBook Air M2 (2022)", "High-performance laptop.", this.itemCategory, 5, "kg", true, 2024, 1, 18);
        this.inventoryItem_SecondConstructor = new InventoryItem("iPhone 15 Pro", itemCategory, 13, false, 2022, 12, 20);
        this.itemCategory = new ItemCategory("Electronics", "Devices and Gadgets.");
    }

    @Test
    public void testSetName_FirstLetterCapital_Valid() {
        this.inventoryItem_Initial.setName("macBook Pro 15");
        assertEquals("MacBook Pro 15", this.inventoryItem_Initial.getName());
    }

    @Test
    public void testSetName_NullName_Invalid() {
        assertThatThrownBy(() -> inventoryItem_Initial.setName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Name of item cannot be null or empty");
    }

    @Test
    public void testSetName_EmptyName_Invalid() {
        assertThatThrownBy(() -> inventoryItem_SecondConstructor.setName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Name of item cannot be null or empty");
    }

    @Test
    public void testSetDescription_Null_Valid() {
        this.inventoryItem_Initial.setDescription(null);
        assertThat(inventoryItem_Initial.getDescription()).isNull();
    }

    @Test
    public void testSetDescription_EmptyDescription_Valid() {
        this.inventoryItem_SecondConstructor.setDescription("");
        assertThat(inventoryItem_SecondConstructor.getDescription()).isNull();
    }

    @Test
    public void testSetDescription_InvalidDescriptionCorrection_Valid() {
        String validDescription = "The MacBook Pro 15” is a powerful and sleek laptop designed for professionals who need high performance and reliability. " +
                "It features a stunning Retina display, a fast processor (Intel Core i7/i9 or Apple M-series), and SSD storage for seamless multitasking. " +
                "With long battery life, a premium aluminum build, and macOS optimization, it’s ideal for creatives, developers, and power users.";

        String invalidDescription = "the MacBook Pro 15” is a powerful and sleek laptop " +
                "designed for professionals who need high performance and reliability. " +
                "It features a stunning Retina display, a fast processor (Intel Core i7/i9 or Apple M-series), " +
                "and SSD storage for seamless multitasking. with long battery life, a premium aluminum build, " +
                "and macOS optimization, it’s ideal for creatives, developers, and power users";

        inventoryItem_SecondConstructor.setDescription(invalidDescription);
        assertEquals(validDescription, inventoryItem_SecondConstructor.getDescription());
    }

    @Test
    public void testSetQuantity_Valid() {
        this.inventoryItem_Initial.setQuantity(117);
        assertEquals(117, this.inventoryItem_Initial.getQuantity());
    }

    @Test
    public void testSetQuantity_NegativeQuantity_Invalid() {
        assertThatThrownBy(() -> inventoryItem_Initial.setQuantity(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Quantity of item cannot be negative");
    }

    @Test
    public void testSetQuantity_MoreThan1000Quantity_Invalid() {
        assertThatThrownBy(() -> inventoryItem_SecondConstructor.setQuantity(1001))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Quantity of item cannot be greater than 1000");
    }

    @Test
    public void testSetUnitOfMeasure_ValidValues() {
        inventoryItem_SecondConstructor.setUnitOfMeasure("kg");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("kilograms");

        inventoryItem_SecondConstructor.setUnitOfMeasure("gr");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("grams");

        inventoryItem_SecondConstructor.setUnitOfMeasure("lb");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("pounds");

        inventoryItem_SecondConstructor.setUnitOfMeasure("mm");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("millimetres");

        inventoryItem_SecondConstructor.setUnitOfMeasure("l");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("liter(s)");

        inventoryItem_SecondConstructor.setUnitOfMeasure("piece");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("piece(s)");
    }

    @Test
    public void testSetUnitOfMeasure_CaseInsensitive() {
        inventoryItem_SecondConstructor.setUnitOfMeasure("Kg");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("kilograms");

        inventoryItem_SecondConstructor.setUnitOfMeasure("GR");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("grams");

        inventoryItem_SecondConstructor.setUnitOfMeasure("Lb");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isEqualTo("pounds");
    }

    @Test
    public void testSetUnitOfMeasure_InvalidValue_SetsNull() {
        inventoryItem_SecondConstructor.setUnitOfMeasure("xyz");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isNull();
    }

    @Test
    public void testSetUnitOfMeasure_EmptyString_SetsNull() {
        inventoryItem_SecondConstructor.setUnitOfMeasure("");
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isNull();
    }

    @Test
    public void testSetUnitOfMeasure_Null_SetsNull() {
        inventoryItem_SecondConstructor.setUnitOfMeasure(null);
        assertThat(inventoryItem_SecondConstructor.getUnitOfMeasure()).isNull();
    }

    @Test
    public void testSetBorrowable_True() {
        inventoryItem_Initial.setBorrowable(true);
        assertThat(inventoryItem_Initial.isBorrowable()).isTrue();
    }

    @Test
    public void testSetBorrowable_False() {
        inventoryItem_Initial.setBorrowable(false);
        assertThat(inventoryItem_Initial.isBorrowable()).isFalse();
    }

    @Test
    public void testSetCreatedAt_ValidDate() {
        inventoryItem_Initial.setCreatedAt(2023, 5, 15);
        assertThat(inventoryItem_Initial.getCreatedAt()).isEqualTo(LocalDate.of(2023, 5, 15));
    }

    @Test
    public void testSetCreatedAt_EdgeCase_ValidLeapYear() {
        inventoryItem_Initial.setCreatedAt(2024, 2, 29); // 2024 е високосна година
        assertThat(inventoryItem_Initial.getCreatedAt()).isEqualTo(LocalDate.of(2024, 2, 29));
    }

    @Test
    public void testSetCreatedAt_ThrowsExceptionForInvalidLeapYear() {
        assertThatThrownBy(() -> inventoryItem_Initial.setCreatedAt(2023, 2, 29))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Invalid day for the given month.");
    }

    @Test
    public void testSetCreatedAt_ThrowsExceptionForInvalidDay() {
        assertThatThrownBy(() -> inventoryItem_Initial.setCreatedAt(2023, 2, 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Invalid day for the given month.");
    }

    @Test
    public void testSetCreatedAt_ThrowsExceptionForFutureYear() {
        int futureYear = LocalDate.now().getYear() + 1;
        assertThatThrownBy(() -> inventoryItem_Initial.setCreatedAt(futureYear, 1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Year must be between 2022 and the current year.");
    }

    @Test
    public void testSetCreatedAt_ThrowsExceptionForOldYear() {
        assertThatThrownBy(() -> inventoryItem_Initial.setCreatedAt(2020, 1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Year must be between 2022 and the current year.");
    }

    @Test
    public void testSetCreatedAt_ThrowsExceptionForInvalidMonth() {
        assertThatThrownBy(() -> inventoryItem_Initial.setCreatedAt(2023, 13, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Invalid month, must be [1,12]");
    }

    @Test
    public void testSetCreatedAt_ThrowsExceptionForNegativeDay() {
        assertThatThrownBy(() -> inventoryItem_Initial.setCreatedAt(2023, 3, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Invalid day for the given month.");
    }
}

