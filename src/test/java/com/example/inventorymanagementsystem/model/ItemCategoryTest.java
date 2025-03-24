package com.example.inventorymanagementsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class ItemCategoryTest {
    private ItemCategory itemCategory;

    @BeforeEach
    public void setUp() {
        itemCategory = new ItemCategory();
    }

    @Test
    public void testSetName_Valid_CapitalizesFirstLetter() {
        itemCategory.setName("electronics");
        assertThat(itemCategory.getName()).isEqualTo("Electronics");
    }

    @Test
    public void testSetName_Invalid_CapitalizesMoreThenOneLetter() {
        itemCategory.setName("paper and electronic books");
        assertNotEquals("Paper And Electronic Books", itemCategory.getName());
    }

    @Test
    public void testSetName_Invalid_EmptyName() {
        assertThatThrownBy(() -> itemCategory.setName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Name cannot be empty");
    }

    @Test
    public void testSetName_Invalid_NullName() {
        assertThatThrownBy(() -> itemCategory.setName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Name cannot be empty");
    }

    @Test
    public void testSetDescription_Valid_CapitalizesFirstLetter() {
        itemCategory.setDescription("this is a test.");
        assertThat(itemCategory.getDescription()).isEqualTo("This is a test.");
    }

    @Test
    public void testSetDescription_Valid_AddsTheDotAtTheEnd() {
        itemCategory.setDescription("this is a test");
        assertThat(itemCategory.getDescription()).isEqualTo("This is a test.");
    }

    @Test
    public void testSetDescription_Valid_Nullable() {
        itemCategory.setDescription(null);
        assertNull(itemCategory.getDescription());
    }
}
