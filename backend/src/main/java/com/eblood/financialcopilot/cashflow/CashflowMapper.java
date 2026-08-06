package com.eblood.financialcopilot.cashflow;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CashflowMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    CashflowEntity toEntity(CashflowRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cashflowSheet", ignore = true)
    @Mapping(target = "cashflowVersion", ignore = true)
    @Mapping(target = "cashflowVersionId", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntity(CashflowRequest request, @MappingTarget CashflowEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    CashflowSheetEntity toEntity(CashflowSheetRequest request);

    @Mapping(target = "cashflowSheetId", source = "cashflow.cashflowSheet.id")
    @Mapping(target = "categoryId", source = "cashflow.category.id")
    Cashflow toDomain(CashflowEntity cashflow);

    @Mapping(target = "signedAmountPerMonth", expression = "java(cashflow.signAmountPerMonth())")
    CashflowResponse toResponse(Cashflow cashflow);

    default CashflowResponse toResponse(CashflowEntity cashflow) {
        return toResponse(toDomain(cashflow));
    }

    @Mapping(target = "owner", source = "owner.username")
    CashflowSheetResponse toResponse(CashflowSheetEntity cashflowSheet);

    CashflowCategoryResponse toResponse(CashflowCategoryEntity category);

    @Mapping(target = "id", ignore = true)
    CashflowCategoryEntity toEntity(CashflowCategoryRequest request);
}
