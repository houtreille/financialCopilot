package com.eblood.financialcopilot.household;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "household_member")
@Getter
@Setter
@NoArgsConstructor
public class HouseholdMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "country_of_residence", nullable = false)
    private String countryOfResidence;

    @Column(name = "country_of_employment", nullable = false)
    private String countryOfEmployment;

    @Column(name = "average_monthly_salary", nullable = false)
    private BigDecimal averageMonthlySalary;

    @Column(name = "current_cash", nullable = false)
    private BigDecimal currentCash;
}
