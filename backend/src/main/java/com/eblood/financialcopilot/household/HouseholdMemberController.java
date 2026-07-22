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

    public HouseholdMemberController(HouseholdMemberRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/household-member")
    public ResponseEntity<HouseholdMemberResponse> create(@Valid @RequestBody HouseholdMemberRequest request) {
        HouseholdMember member = new HouseholdMember(
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.countryOfResidence(),
                request.countryOfEmployment(),
                request.averageMonthlySalary(),
                request.currentCash());
        HouseholdMember saved = repository.save(member);
        return ResponseEntity.ok(HouseholdMemberResponse.from(saved));
    }

    @GetMapping("/api/household-member")
    public ResponseEntity<HouseholdMemberResponse> get() {
        return repository.findAll().stream()
                .findFirst()
                .map(HouseholdMemberResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
