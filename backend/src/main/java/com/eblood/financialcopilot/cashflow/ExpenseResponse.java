package com.eblood.financialcopilot.cashflow;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public record ExpenseResponse (
        int id,
        int expenseSheetId,
        BigDecimal amount,
        String label,
        Month month,
        int expenseVersion,
        int expenseVersionId,
        ExpenseType expenseType,
        LocalDate startDate,
        LocalDate endDate,
        String description,
        Long categoryId) {
}
