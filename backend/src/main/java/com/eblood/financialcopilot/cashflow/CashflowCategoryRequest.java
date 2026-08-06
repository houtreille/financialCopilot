package com.eblood.financialcopilot.cashflow;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CashflowCategoryRequest(
        @NotBlank String label,
        @Nullable String color) {
}
