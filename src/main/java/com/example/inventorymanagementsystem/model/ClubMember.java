package com.example.inventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "club_member")
@Getter
@NoArgsConstructor
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    public ClubMember(String name, String email) {
        this.setName(name);
        this.setEmail(email);
    }

    public void setName(String name) {
        //Name cannot be empty
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException(">! Name of club member cannot be empty");

        //We format the name in case of user typo
        StringBuilder formattedName = new StringBuilder();
        //Separate the different names by space
        String[] words = name.split(" ");

        /* For each name we:
        1. Make the first letter capital
        2. The rest remain small
        3. Add the space after each name
        */
        for(String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }

        //Trim the last space as not needed
        this.name = formattedName.toString().trim();
    }

    public void setEmail(String email) {
        if(email == null || email.isEmpty())
            throw new IllegalArgumentException(">! Email of club member cannot be empty");

        else if(email.length() < 8 || email.length() > 35)
            throw new IllegalArgumentException(">! Email length is invalid, must be [8,35]");

        else if(!email.endsWith("@gmail.com") && !email.endsWith("@abv.bg") && !email.endsWith("@icloud.com"))
            throw new IllegalArgumentException(">! Email domain is invalid, must be @[gmail.com|abv.bg|icloud.com]");

        this.email = email.toLowerCase();
    }
}
