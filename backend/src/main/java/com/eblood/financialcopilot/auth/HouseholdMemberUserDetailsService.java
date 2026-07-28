package com.eblood.financialcopilot.auth;

import com.eblood.financialcopilot.household.HouseholdMemberEntity;
import com.eblood.financialcopilot.household.HouseholdMemberRepository;
import com.eblood.financialcopilot.household.RoleEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseholdMemberUserDetailsService implements UserDetailsService {

    private final HouseholdMemberRepository repository;

    public HouseholdMemberUserDetailsService(HouseholdMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HouseholdMemberEntity member = repository.findByUsername(username)
                .filter(candidate -> candidate.getPasswordHash() != null)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown username: " + username));

        return User.withUsername(member.getUsername())
                .password(member.getPasswordHash())
                .authorities(getAuthorities(member).toArray(new String[0]))
                .build();
    }


    private List<String> getAuthorities(HouseholdMemberEntity member) {
        return member.getRoles().stream()
                .map(RoleEntity::getRole)
             //   .map(role -> "ROLE_" + role)
                .toList();
    }
}
