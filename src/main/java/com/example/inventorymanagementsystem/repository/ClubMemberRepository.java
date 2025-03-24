package com.example.inventorymanagementsystem.repository;

import com.example.inventorymanagementsystem.model.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Integer> {
    boolean existsById(int id);
    Optional<ClubMember> findByEmail(String email);
}
