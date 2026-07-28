package com.eblood.financialcopilot.household;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "household_member")
@Getter
@Setter
@NoArgsConstructor
public class HouseholdMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<RoleEntity> roles = new ArrayList<>();
}
