package com.eblood.financialcopilot.cashflow;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashflowResponse (
        int id,
        int cashflowSheetId,
        BigDecimal amount,
        BigDecimal signedAmountPerMonth,
        String label,
        String month,
        int cashflowVersion,
        int cashflowVersionId,
        CashflowType cashflowType,
        CashflowDirection direction,
        LocalDate startDate,
        LocalDate endDate,
        String description,
        Long categoryId) {
}
