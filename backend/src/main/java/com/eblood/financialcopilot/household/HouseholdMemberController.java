package com.eblood.financialcopilot.household;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HouseholdMemberController {

    private final HouseholdMemberService service;

    public HouseholdMemberController(HouseholdMemberService service) {
        this.service = service;
    }

    @PostMapping("/api/household-member")
    public ResponseEntity<HouseholdMemberResponse> create(@Valid @RequestBody HouseholdMemberRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/api/household-member")
    public ResponseEntity<HouseholdMemberResponse> get() {
        return service.get()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
