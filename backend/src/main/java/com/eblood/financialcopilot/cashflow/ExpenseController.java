package com.eblood.financialcopilot.cashflow;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService service;


    @PostMapping("/api/expense")
    private ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest expenseRequest) {
        return ResponseEntity.ok(service.create(expenseRequest));
    }

    @GetMapping("/api/expenses")
    private ResponseEntity<List<ExpenseResponse>> findAll() {
        return ResponseEntity.ok(service.findAllExpenses());
    }

    @PostMapping("/api/expense-sheet")
    private ResponseEntity<ExpenseSheetResponse> create(@Valid @RequestBody ExpenseSheetRequest expenseSheetRequest) {
        return ResponseEntity.ok(service.create(expenseSheetRequest));
    }

    @GetMapping("/api/expense-sheets")
    private ResponseEntity<List<ExpenseSheetResponse>> findAllByOwner(@RequestParam(value = "owner" , required = true) String owner) {
        return ResponseEntity.ok(service.findAllExpenseSheets(owner));
    }


}
