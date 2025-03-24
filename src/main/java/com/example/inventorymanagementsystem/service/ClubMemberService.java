package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.ClubMember;
import com.example.inventorymanagementsystem.repository.ClubMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubMemberService {
    private final ClubMemberRepository clubMemberRepository;

    public ClubMemberService(ClubMemberRepository clubMemberRepository) {
        this.clubMemberRepository = clubMemberRepository;
    }

    public void addClubMember(ClubMember clubMember) {
        this.clubMemberRepository.save(clubMember);
    }

    public boolean deleteClubMember(int memberId) {
        if(clubMemberRepository.existsById(memberId)){
            clubMemberRepository.deleteById(memberId);
            return true;
        }

        return false;
    }

    public Optional<ClubMember> getClubMemberById(int id) {
        return clubMemberRepository.findById(id);
    }

    public Optional<ClubMember> getClubMemberByEmail(String email) {
        return clubMemberRepository.findByEmail(email);
    }

    public List<ClubMember> getAllClubMembers() {
        return clubMemberRepository.findAll();
    }
}
