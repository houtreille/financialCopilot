package com.eblood.financialcopilot.cashflow;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class CashflowTest {

    @Test
    void monthlyOutflowIsNegated() {
        var cashflow = cashflowOf(new BigDecimal("100.00"), CashflowType.MONTHLY, CashflowDirection.OUTFLOW);
        assertThat(cashflow.signAmountPerMonth()).isEqualByComparingTo("-100.00");
    }

    @Test
    void monthlyInflowStaysPositive() {
        var cashflow = cashflowOf(new BigDecimal("100.00"), CashflowType.MONTHLY, CashflowDirection.INFLOW);
        assertThat(cashflow.signAmountPerMonth()).isEqualByComparingTo("100.00");
    }

    @Test
    void yearlyOutflowIsNegatedAndDividedByTwelve() {
        var cashflow = cashflowOf(new BigDecimal("1200.00"), CashflowType.YEARLY, CashflowDirection.OUTFLOW);
        assertThat(cashflow.signAmountPerMonth()).isEqualByComparingTo("-100.00");
    }

    @Test
    void yearlyInflowIsDividedByTwelve() {
        var cashflow = cashflowOf(new BigDecimal("1200.00"), CashflowType.YEARLY, CashflowDirection.INFLOW);
        assertThat(cashflow.signAmountPerMonth()).isEqualByComparingTo("100.00");
    }

    private static Cashflow cashflowOf(BigDecimal amount, CashflowType type, CashflowDirection direction) {
        return new Cashflow(1L, 1L, amount, "label", Month.JANUARY, 1, 1, type, direction, null, null, null, null);
    }
}
