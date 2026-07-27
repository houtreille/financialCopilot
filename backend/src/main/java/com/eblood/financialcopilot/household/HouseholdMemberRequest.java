package com.eblood.financialcopilot.household;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record HouseholdMemberRequest(
        @NotNull @PastOrPresent LocalDate dateOfBirth,
        @NotBlank String countryOfResidence,
        @NotBlank String countryOfEmployment,
        @NotNull BigDecimal averageMonthlySalary,
        @NotNull BigDecimal currentCash) {
}
