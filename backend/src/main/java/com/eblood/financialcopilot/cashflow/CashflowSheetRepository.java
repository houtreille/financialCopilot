package com.eblood.financialcopilot.cashflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashflowSheetRepository extends JpaRepository<CashflowSheetEntity, Long> {
    List<CashflowSheetEntity> findAllByOwnerUsername(String username);
}
