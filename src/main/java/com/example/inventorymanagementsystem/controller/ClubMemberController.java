package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.model.ClubMember;
import com.example.inventorymanagementsystem.service.ClubMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/club_member")
public class ClubMemberController {
    private final ClubMemberService clubMemberService;

    public ClubMemberController(ClubMemberService clubMemberService) {
        this.clubMemberService = clubMemberService;
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Optional<ClubMember>> getClubMemberById(@PathVariable Integer id) {
        return ResponseEntity.ok(clubMemberService.getClubMemberById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClubMember>> getAllClubMember() {
        return ResponseEntity.ok(clubMemberService.getAllClubMembers());
    }

    @GetMapping("/member/")
    public ResponseEntity<Optional<ClubMember>> getClubMemberByEmail(@RequestParam String email) {
        Optional<ClubMember> member = clubMemberService.getClubMemberByEmail(email);

        if(member.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(member);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addClubMember(@RequestBody ClubMember clubMember) {
        Optional<ClubMember> existing = clubMemberService.getClubMemberByEmail(clubMember.getEmail());

        if (existing.isPresent())
            return ResponseEntity.badRequest().body(">! Member with this email already exists");

        clubMemberService.addClubMember(clubMember);
        return ResponseEntity.ok("[Member added successfully]");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClubMember(@PathVariable Integer id) {
        boolean deleted = clubMemberService.deleteClubMember(id);
        return deleted ? ResponseEntity.ok("[Member deleted successfully]") : ResponseEntity.badRequest().body(">! Member not found");
    }
}
