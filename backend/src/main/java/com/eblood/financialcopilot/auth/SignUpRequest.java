package com.eblood.financialcopilot.auth;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull @PastOrPresent LocalDate dateOfBirth,
        @NotBlank String countryOfResidence,
        @NotBlank String countryOfEmployment,
        @NotNull BigDecimal averageMonthlySalary,
        @NotNull BigDecimal currentCash) {
}
