package com.eblood.financialcopilot.household;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HouseholdMemberController {

    private final HouseholdMemberRepository repository;
    private final HouseholdMemberMapper mapper;

    public HouseholdMemberController(HouseholdMemberRepository repository, HouseholdMemberMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping("/api/household-member")
    public ResponseEntity<HouseholdMemberResponse> create(@Valid @RequestBody HouseholdMemberRequest request) {
        HouseholdMember saved = repository.save(mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @GetMapping("/api/household-member")
    public ResponseEntity<HouseholdMemberResponse> get() {
        return repository.findAll().stream()
                .findFirst()
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
