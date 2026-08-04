package com.eblood.financialcopilot.cashflow;

import com.eblood.financialcopilot.household.HouseholdMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSheetRepository expenseSheetRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final ExpenseMapper mapper;

    @Transactional(readOnly = true)
    public List<ExpenseResponse> findAllExpenses() {
        return expenseRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ExpenseSheetResponse> findAllExpenseSheets(String owner) {
        return expenseSheetRepository.findAllByOwnerUsername(owner).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExpenseSheetResponse findExpenseSheetById(Long id) {
        return expenseSheetRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Expense sheet not found: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<ExpenseResponse> findLatestExpenseByLabel(String label) {
        return expenseRepository.findAllByLabelOrderByExpenseVersionIdDesc(label).stream()
                .findFirst()
                .map(mapper::toResponse);
    }

    @Transactional
    public ExpenseSheetResponse create(ExpenseSheetRequest expenseSheetRequest) {
        var owner = householdMemberRepository.findByUsername(expenseSheetRequest.owner())
                .orElseThrow(() -> new EntityNotFoundException("Household member not found: " + expenseSheetRequest.owner()));
        var entity = mapper.toEntity(expenseSheetRequest);
        entity.setOwner(owner);
        var saved = expenseSheetRepository.save(entity);
        return mapper.toResponse(saved);
    }


    @Transactional
    public ExpenseResponse create(ExpenseRequest expenseRequest) {
        var expenseSheet = expenseSheetRepository.findById(expenseRequest.expenseSheetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Expense sheet not found: " + expenseRequest.expenseSheetId()));

        var expense = mapper.toEntity(expenseRequest);
        expense.setExpenseSheet(expenseSheet);
        var saved = expenseRepository.save(expense);
        return mapper.toResponse(saved);
    }

    @Transactional
    public void deleteExpenseSheet(Long id) {
        expenseSheetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Expense sheet not found: " + id));
        expenseSheetRepository.deleteById(id);
    }

    @Transactional
    public void deleteExpense(Long id) {
        expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Expense not found: " + id));
        expenseRepository.deleteById(id);
    }
}
