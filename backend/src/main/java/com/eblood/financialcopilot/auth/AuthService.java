package com.eblood.financialcopilot.auth;

import com.eblood.financialcopilot.household.HouseholdMember;
import com.eblood.financialcopilot.household.HouseholdMemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final HouseholdMemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public AuthService(HouseholdMemberRepository repository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public CurrentUserResponse signUp(SignUpRequest request) {
        if (repository.findByUsername(request.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }

        HouseholdMember member = new HouseholdMember();
        member.setUsername(request.username());
        member.setPasswordHash(passwordEncoder.encode(request.password()));
        member.setFirstName(request.firstName());
        member.setLastName(request.lastName());
        member.setDateOfBirth(request.dateOfBirth());
        member.setCountryOfResidence(request.countryOfResidence());
        member.setCountryOfEmployment(request.countryOfEmployment());
        member.setAverageMonthlySalary(request.averageMonthlySalary());
        member.setCurrentCash(request.currentCash());

        HouseholdMember saved = repository.save(member);
        return toResponse(saved);
    }

    public CurrentUserResponse login(LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, httpRequest, httpResponse);

        return currentUser();
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }

    @Transactional(readOnly = true)
    public CurrentUserResponse currentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HouseholdMember member = repository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return toResponse(member);
    }

    private CurrentUserResponse toResponse(HouseholdMember member) {
        return new CurrentUserResponse(member.getId(), member.getUsername(), member.getFirstName(), member.getLastName());
    }
}
