package com.eblood.financialcopilot.cashflow;

import java.util.List;

public record ExpenseSheetResponse(
        Long id,
        String sheetName,
        String owner,
        List<ExpenseResponse> expenses) {
}
