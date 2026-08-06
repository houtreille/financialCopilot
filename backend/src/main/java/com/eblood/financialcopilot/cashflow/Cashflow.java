package com.eblood.financialcopilot.cashflow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;

public record Cashflow(
        Long id,
        Long cashflowSheetId,
        BigDecimal amount,
        String label,
        Month month,
        int cashflowVersion,
        int cashflowVersionId,
        CashflowType cashflowType,
        CashflowDirection direction,
        LocalDate startDate,
        LocalDate endDate,
        String description,
        Long categoryId) {

    private static final BigDecimal MONTHS_PER_YEAR = BigDecimal.valueOf(12);

    public BigDecimal signAmountPerMonth() {
        BigDecimal signedAmount = direction == CashflowDirection.OUTFLOW ? amount.negate() : amount;
        return cashflowType == CashflowType.YEARLY
                ? signedAmount.divide(MONTHS_PER_YEAR, 2, RoundingMode.HALF_UP)
                : signedAmount;
    }
}
