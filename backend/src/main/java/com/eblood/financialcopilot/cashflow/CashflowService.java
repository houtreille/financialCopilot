package com.eblood.financialcopilot.cashflow;

import com.eblood.financialcopilot.household.HouseholdMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashflowService {

    private final CashflowRepository cashflowRepository;
    private final CashflowSheetRepository cashflowSheetRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final CashflowCategoryRepository cashflowCategoryRepository;
    private final CashflowMapper mapper;

    @Transactional(readOnly = true)
    public List<CashflowResponse> findAllCashflows() {
        return cashflowRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<CashflowSheetResponse> findAllCashflowSheets(String owner) {
        return cashflowSheetRepository.findAllByOwnerUsername(owner).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CashflowSheetResponse findCashflowSheetById(Long id) {
        return cashflowSheetRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cashflow sheet not found: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<CashflowResponse> findLatestCashflowByLabel(String label) {
        return cashflowRepository.findAllByLabelOrderByCashflowVersionIdDesc(label).stream()
                .findFirst()
                .map(mapper::toResponse);
    }

    @Transactional
    public CashflowSheetResponse create(CashflowSheetRequest cashflowSheetRequest) {
        var owner = householdMemberRepository.findByUsername(cashflowSheetRequest.owner())
                .orElseThrow(() -> new EntityNotFoundException("Household member not found: " + cashflowSheetRequest.owner()));
        var entity = mapper.toEntity(cashflowSheetRequest);
        entity.setOwner(owner);
        var saved = cashflowSheetRepository.save(entity);
        return mapper.toResponse(saved);
    }


    @Transactional
    public CashflowResponse create(CashflowRequest cashflowRequest) {
        var cashflowSheet = cashflowSheetRepository.findById(cashflowRequest.cashflowSheetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cashflow sheet not found: " + cashflowRequest.cashflowSheetId()));

        var cashflow = mapper.toEntity(cashflowRequest);
        cashflow.setCashflowSheet(cashflowSheet);
        cashflow.setCategory(resolveCategory(cashflowRequest.categoryId()));
        var saved = cashflowRepository.save(cashflow);
        return mapper.toResponse(saved);
    }

    @Transactional
    public CashflowResponse update(Long id, CashflowRequest request) {
        var cashflow = cashflowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cashflow not found: " + id));
        mapper.updateEntity(request, cashflow);
        cashflow.setCategory(resolveCategory(request.categoryId()));
        var saved = cashflowRepository.save(cashflow);
        return mapper.toResponse(saved);
    }

    private @Nullable CashflowCategoryEntity resolveCategory(@Nullable Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        return cashflowCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cashflow category not found: " + categoryId));
    }

    @Transactional
    public void deleteCashflowSheet(Long id) {
        cashflowSheetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cashflow sheet not found: " + id));
        cashflowSheetRepository.deleteById(id);
    }

    @Transactional
    public void deleteCashflow(Long id) {
        cashflowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cashflow not found: " + id));
        cashflowRepository.deleteById(id);
    }

    @Transactional
    public CashflowCategoryResponse createCategory(CashflowCategoryRequest cashflowCategoryRequest) {
        var entityToSave = mapper.toEntity(cashflowCategoryRequest);
        return mapper.toResponse(cashflowCategoryRepository.save(entityToSave));
    }

    @Transactional(readOnly = true)
    public List<CashflowCategoryResponse> findAllCategories() {
        return cashflowCategoryRepository.findAll().stream().map(mapper::toResponse).toList();
    }
}
