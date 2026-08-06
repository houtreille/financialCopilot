package com.eblood.financialcopilot.cashflow;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public record CashflowRequest (
        @NotNull Long cashflowSheetId,
        @NotNull  BigDecimal amount,
        @NotNull Month month,
        @NotNull String label,
        @NotNull CashflowType cashflowType,
        @NotNull CashflowDirection direction,
        @Nullable LocalDate startDate,
        @Nullable LocalDate endDate,
        @Nullable String description,
        @Nullable Long categoryId){}
