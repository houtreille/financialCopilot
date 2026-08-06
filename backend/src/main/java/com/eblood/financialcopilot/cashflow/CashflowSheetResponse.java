package com.eblood.financialcopilot.cashflow;

import java.util.List;

public record CashflowSheetResponse(
        Long id,
        String sheetName,
        String owner,
        List<CashflowResponse> cashflows,
        String type) {
}
