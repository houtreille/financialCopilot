package com.eblood.financialcopilot.household;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdMemberRepository extends JpaRepository<HouseholdMemberEntity, Long> {

    Optional<HouseholdMemberEntity> findByUsername(String username);
}
