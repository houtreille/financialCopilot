package com.eblood.financialcopilot.household;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HouseholdMemberMapper {

    @Mapping(target = "id", ignore = true)
    HouseholdMember toEntity(HouseholdMemberRequest request);

    HouseholdMemberResponse toResponse(HouseholdMember member);
}
