package com.eblood.financialcopilot.auth;

import com.eblood.financialcopilot.household.HouseholdMemberEntity;
import com.eblood.financialcopilot.household.HouseholdMemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

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

        HouseholdMemberEntity member = new HouseholdMemberEntity();
        member.setUsername(request.username());
        member.setPasswordHash(passwordEncoder.encode(request.password()));
        member.setDateOfBirth(request.dateOfBirth());
        member.setCountryOfResidence(request.countryOfResidence());
        member.setCountryOfEmployment(request.countryOfEmployment());
        member.setAverageMonthlySalary(request.averageMonthlySalary());
        member.setCurrentCash(request.currentCash());

        HouseholdMemberEntity saved = repository.save(member);
        return toResponse(saved, false);
    }

    public CurrentUserResponse login(LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (AuthenticationException e) {
            log.warn("Login failed for username '{}'", request.username(), e);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HouseholdMemberEntity member = repository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        return toResponse(member, isAdmin);
    }

    private CurrentUserResponse toResponse(HouseholdMemberEntity member, boolean isAdmin) {
        return new CurrentUserResponse(member.getId(), member.getUsername(), isAdmin);
    }
}
