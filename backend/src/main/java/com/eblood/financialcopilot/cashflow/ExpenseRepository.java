package com.eblood.financialcopilot.cashflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findAllByLabelOrderByExpenseVersionIdDesc(String label);
}
