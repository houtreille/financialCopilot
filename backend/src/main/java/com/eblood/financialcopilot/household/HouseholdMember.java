package com.eblood.financialcopilot.household;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "household_member")
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

    protected HouseholdMember() {
    }

    public HouseholdMember(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String countryOfResidence,
            String countryOfEmployment,
            BigDecimal averageMonthlySalary,
            BigDecimal currentCash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.countryOfResidence = countryOfResidence;
        this.countryOfEmployment = countryOfEmployment;
        this.averageMonthlySalary = averageMonthlySalary;
        this.currentCash = currentCash;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public String getCountryOfEmployment() {
        return countryOfEmployment;
    }

    public BigDecimal getAverageMonthlySalary() {
        return averageMonthlySalary;
    }

    public BigDecimal getCurrentCash() {
        return currentCash;
    }
}
