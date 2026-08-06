package com.eblood.financialcopilot.cashflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashflowRepository extends JpaRepository<CashflowEntity, Long> {
    List<CashflowEntity> findAllByLabelOrderByCashflowVersionIdDesc(String label);
}
