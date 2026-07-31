package com.eblood.financialcopilot.cashflow;

import java.math.BigDecimal;
import java.time.Month;

public record ExpenseResponse (
        int id,
        int expenseSheetId,
        BigDecimal amount,
        String label,
        Month month,
        int expenseVersion,
        int expenseVersionId,
        ExpenseType expenseType) {
}
