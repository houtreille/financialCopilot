package com.eblood.financialcopilot.cashflow;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Entity
@Getter
@Setter
@Table(name = "expense")
@NoArgsConstructor
public class CashflowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "month", nullable = false)
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(name = "expense_version", nullable = false)
    private int cashflowVersion;

    @Column(name = "expense_version_id", nullable = false)
    private int cashflowVersionId;

    @Column(name = "expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CashflowType cashflowType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_sheet_id", referencedColumnName = "id")
    private CashflowSheetEntity cashflowSheet;

    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CashflowCategoryEntity category;

    @Column(name = "direction", nullable = false)
    @Enumerated(EnumType.STRING)
    private CashflowDirection direction;
}
