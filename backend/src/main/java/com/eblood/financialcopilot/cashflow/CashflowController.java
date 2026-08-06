package com.eblood.financialcopilot.cashflow;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CashflowController {
    private final CashflowService service;


    @PostMapping("/api/cashflow")
    private ResponseEntity<CashflowResponse> create(@Valid @RequestBody CashflowRequest cashflowRequest) {
        return ResponseEntity.ok(service.create(cashflowRequest));
    }

    @GetMapping("/api/cashflows")
    private ResponseEntity<List<CashflowResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCashflows());
    }

    @PostMapping("/api/cashflow-sheet")
    private ResponseEntity<CashflowSheetResponse> create(@Valid @RequestBody CashflowSheetRequest cashflowSheetRequest) {
        return ResponseEntity.ok(service.create(cashflowSheetRequest));
    }

    @GetMapping("/api/cashflow-sheets")
    private ResponseEntity<List<CashflowSheetResponse>> findAllByOwner(@RequestParam(value = "owner" , required = true) String owner) {
        return ResponseEntity.ok(service.findAllCashflowSheets(owner));
    }

    @GetMapping("/api/cashflow-sheets/{id}")
    private ResponseEntity<CashflowSheetResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findCashflowSheetById(id));
    }


    @DeleteMapping("/api/cashflow-sheets/{id}")
    private ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteCashflowSheet(id);
        return ResponseEntity.ok("Cashflow deleted");
    }

    @DeleteMapping("/api/cashflow/{id}")
    private ResponseEntity<String> deleteCashflow(@PathVariable Long id) {
        service.deleteCashflow(id);
        return ResponseEntity.ok("Cashflow deleted");
    }

    @PutMapping("/api/cashflow/{id}")
    private ResponseEntity<CashflowResponse> updateCashflow(@PathVariable Long id, @Valid @RequestBody CashflowRequest cashflowRequest) {
        return ResponseEntity.ok(service.update(id, cashflowRequest));
    }

    @PostMapping("/api/cashflow-category")
    private ResponseEntity<CashflowCategoryResponse> create(@Valid @RequestBody CashflowCategoryRequest cashflowCategoryRequest) {
        return ResponseEntity.ok(service.createCategory(cashflowCategoryRequest));
    }

    @GetMapping("/api/cashflow-categories")
    private ResponseEntity<List<CashflowCategoryResponse>> findAllCategories() {
        return ResponseEntity.ok(service.findAllCategories());
    }

}
