package com.eblood.financialcopilot.household;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HouseholdMemberService {

    private final HouseholdMemberRepository repository;
    private final HouseholdMemberMapper mapper;

    public HouseholdMemberService(HouseholdMemberRepository repository, HouseholdMemberMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public HouseholdMemberResponse create(HouseholdMemberRequest request) {
        HouseholdMember saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<HouseholdMemberResponse> get() {
        return repository.findAll().stream().findFirst().map(mapper::toResponse);
    }
}
