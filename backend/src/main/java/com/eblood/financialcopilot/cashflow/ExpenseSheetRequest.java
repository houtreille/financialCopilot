package com.eblood.financialcopilot.cashflow;

import jakarta.validation.constraints.NotBlank;

public record ExpenseSheetRequest(
        @NotBlank String sheetName,
        @NotBlank String owner) {
}
