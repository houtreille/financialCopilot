package com.eblood.financialcopilot.household;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HouseholdMemberResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String countryOfResidence,
        String countryOfEmployment,
        BigDecimal averageMonthlySalary,
        BigDecimal currentCash) {

    static HouseholdMemberResponse from(HouseholdMember member) {
        return new HouseholdMemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getDateOfBirth(),
                member.getCountryOfResidence(),
                member.getCountryOfEmployment(),
                member.getAverageMonthlySalary(),
                member.getCurrentCash());
    }
}
