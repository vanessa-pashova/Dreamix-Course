package com.example.inventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class ClubMemberTest {
    private ClubMember member;

    @BeforeEach
    public void setUp() {
        member = new ClubMember("Vani Pashi", "vani@gmail.com");
    }

    @Test
    public void testSetName_Valid() {
        member.setName("Vani Pashi");
        assertTrue("Vani Pashi".equals(member.getName()));
    }

    @Test
    public void testSetName_InvalidName_Valid() {
        member.setName("vani pashi");
        assertEquals("Vani Pashi", member.getName());
    }

    @Test
    public void testSetName_Null_Invalid() {
        assertThatThrownBy(() -> member.setName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Name of club member cannot be empty");
    }

    @Test
    public void testSetName_Empty_Invalid() {
        assertThatThrownBy(() -> member.setName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Name of club member cannot be empty");
    }

    @Test
    public void testSetEmail_Valid() {
        member.setEmail("vAni.pAshi@gmail.com");
        assertEquals("vani.pashi@gmail.com", member.getEmail());
    }

    @Test
    public void testSetEmail_invalidDomain_Invalid() {
        assertThatThrownBy(() -> member.setEmail("vani@yahoo.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Email domain is invalid, must be @[gmail.com|abv.bg|icloud.com]");
    }

    @Test
    public void testSetEmail_InvalidLength_Invalid() {
        assertThatThrownBy(() -> member.setEmail("@abv.bg"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Email length is invalid, must be [8,35]");

        assertThatThrownBy(() -> member.setEmail("na_baba_spuza_svinskoto_top@icould.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Email length is invalid, must be [8,35]");
    }

    @Test
    public void testSetEmail_Null_Invalid() {
        assertThatThrownBy(() -> member.setEmail(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Email of club member cannot be empty");
    }

    @Test
    public void testSetEmail_Empty_Invalid() {
        assertThatThrownBy(() -> member.setEmail(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(">! Email of club member cannot be empty");
    }
}
