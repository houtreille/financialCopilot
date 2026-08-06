package com.eblood.financialcopilot.cashflow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CashflowSheetRequest(
        @NotBlank String sheetName,
        @NotBlank String owner,
        @NotNull CashflowSheetType type) {
}
