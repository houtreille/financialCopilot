package com.eblood.financialcopilot.cashflow;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Month;

public record ExpenseRequest (
    @NotNull Long expenseSheetId,
    @NotNull  BigDecimal amount,
    @NotNull Month month,
    @NotNull String label,
    @NotNull ExpenseType expenseType){}
