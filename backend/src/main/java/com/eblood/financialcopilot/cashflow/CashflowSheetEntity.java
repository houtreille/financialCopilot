package com.eblood.financialcopilot.cashflow;

import com.eblood.financialcopilot.household.HouseholdMemberEntity;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "expense_sheet")
@NoArgsConstructor
public class CashflowSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sheet_name")
    private String sheetName;

    @OneToMany(mappedBy = "cashflowSheet", fetch = FetchType.LAZY)
    private List<CashflowEntity> cashflows;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private HouseholdMemberEntity owner;

    @Column(name = "expense_sheet_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CashflowSheetType type;

}
