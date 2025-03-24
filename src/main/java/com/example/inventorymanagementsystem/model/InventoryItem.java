package com.example.inventorymanagementsystem.model;

import java.time.LocalDate;
import java.time.YearMonth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory_item")
@Getter
@NoArgsConstructor
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ItemCategory itemCategory;

    @Column(nullable = false)
    private int quantity;

    @Column
    private String unitOfMeasure;

    @Column(nullable = false)
    private boolean isBorrowable;

    @Column(nullable = false)
    private LocalDate createdAt;

    //Initial Constructor with all the arguments
    public InventoryItem(String name, String description, ItemCategory itemCategory, int quantity, String unitOfMeasure, boolean isBorrowable, int year, int month, int day) {
        this.setName(name);
        this.setDescription(description);
        this.setItemCategory(itemCategory);
        this.setQuantity(quantity);
        this.setUnitOfMeasure(unitOfMeasure);
        this.setBorrowable(isBorrowable);
        this.setCreatedAt(year, month, day);
    }

    //Second constructor as description and unitOfMeasure can be nullable
    public InventoryItem(String name, ItemCategory itemCategory, int quantity, boolean isBorrowable, int year, int month, int day) {
        this.setName(name);
        this.setItemCategory(itemCategory);
        this.setQuantity(quantity);
        this.setBorrowable(isBorrowable);
        this.setCreatedAt(year, month, day);
    }

    public void setName(String name) {
        //Name cannot be empty
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException(">! Name of item cannot be null or empty");

        //First letter must be always a capital one
        this.name = name.toUpperCase().charAt(0) + name.substring(1);
    }

    public void setDescription(String description) {
        //Item description can be empty as there's nothing to describe
        if(description == null || description.isEmpty()) {
            this.description = null;
            return;
        }

        //After every dot there is a capital letter
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char symbol : description.toCharArray()) {
            if (capitalizeNext && Character.isLetter(symbol)) {
                result.append(Character.toUpperCase(symbol));
                capitalizeNext = false;
            }

            else
                result.append(symbol);

            if (symbol == '.')
                capitalizeNext = true;
        }

        //If the last symbol is not a dot - to be added one
        if(result.charAt(result.length() - 1) != '.')
            result.append('.');

        this.description = result.toString();
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setQuantity(int quantity) {
        if(quantity < 0)
            throw new IllegalArgumentException(">! Quantity of item cannot be negative");

        if(quantity > 1000)
            throw new IllegalArgumentException(">! Quantity of item cannot be greater than 1000");

        this.quantity = quantity;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        //As there can be items that are not measurable
        if(unitOfMeasure == null || unitOfMeasure.isEmpty()) {
            this.unitOfMeasure = null;
            return;
        }

        //Applying one of these options and none if not given
        switch(unitOfMeasure.toLowerCase()) {
            case "kg" -> this.unitOfMeasure = "kilograms";
            case "gr" -> this.unitOfMeasure = "grams";
            case "lb" -> this.unitOfMeasure = "pounds";
            case "mm" -> this.unitOfMeasure = "millimetres";
            case "l" -> this.unitOfMeasure = "liter(s)";
            case "piece" -> this.unitOfMeasure = "piece(s)";
            default -> this.unitOfMeasure = null;
        }
    }

    public void setBorrowable(boolean borrowable) {
        this.isBorrowable = borrowable;
    }

    //Validating the date
    public void setCreatedAt(int year, int month, int day) {
        if (year < 2022 || year > LocalDate.now().getYear())
            throw new IllegalArgumentException(">! Year must be between 2022 and the current year.");

        if (month < 1 || month > 12)
            throw new IllegalArgumentException(">! Invalid month, must be [1,12]");

        YearMonth yearMonth = YearMonth.of(year, month);
        int maxDays = yearMonth.lengthOfMonth();

        if (day < 1 || day > maxDays)
            throw new IllegalArgumentException(">! Invalid day for the given month.");

        this.createdAt = LocalDate.of(year, month, day);
    }
}
