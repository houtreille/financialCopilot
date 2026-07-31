package com.eblood.financialcopilot.household;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseholdMemberService {

    private final HouseholdMemberRepository repository;
    private final HouseholdMemberMapper mapper;

    public HouseholdMemberResponse create(HouseholdMemberRequest request) {
        HouseholdMemberEntity saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<HouseholdMemberResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
