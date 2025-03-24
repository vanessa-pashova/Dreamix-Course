package com.example.inventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_category")
//Generates the Getters automatically
@Getter
@NoArgsConstructor
public class ItemCategory {
    //Unique key for the DB, JPA will take care for the incrementation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 256)
    private String description;

    public ItemCategory(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public ItemCategory(String name) {
        this.setName(name);
    }

    public void setName(String name) {
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException(">! Name cannot be empty");
        this.name = name.toUpperCase().charAt(0) + name.substring(1).toLowerCase();
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
}
