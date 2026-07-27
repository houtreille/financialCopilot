package com.eblood.financialcopilot.household;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HouseholdMemberResponse(
        Long id,
        LocalDate dateOfBirth,
        String countryOfResidence,
        String countryOfEmployment,
        BigDecimal averageMonthlySalary,
        BigDecimal currentCash) {
}
