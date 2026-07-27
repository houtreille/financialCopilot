package com.eblood.financialcopilot.auth;

import com.eblood.financialcopilot.household.HouseholdMember;
import com.eblood.financialcopilot.household.HouseholdMemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HouseholdMemberUserDetailsService implements UserDetailsService {

    private final HouseholdMemberRepository repository;

    public HouseholdMemberUserDetailsService(HouseholdMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HouseholdMember member = repository.findByUsername(username)
                .filter(candidate -> candidate.getPasswordHash() != null)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown username: " + username));

        return User.withUsername(member.getUsername())
                .password(member.getPasswordHash())
                .authorities("ROLE_USER")
                .build();
    }
}
