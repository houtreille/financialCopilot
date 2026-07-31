package com.eblood.financialcopilot.cashflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseSheetRepository extends JpaRepository<ExpenseSheetEntity, Long> {
    List<ExpenseSheetEntity> findAllByOwnerUsername(String username);
}
