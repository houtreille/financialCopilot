package com.eblood.financialcopilot.cashflow;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    ExpenseEntity toEntity(ExpenseRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expenseSheet", ignore = true)
    @Mapping(target = "expenseVersion", ignore = true)
    @Mapping(target = "expenseVersionId", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntity(ExpenseRequest request, @MappingTarget ExpenseEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    ExpenseSheetEntity toEntity(ExpenseSheetRequest request);

    @Mapping(target = "expenseSheetId", source = "member.expenseSheet.id")
    @Mapping(target = "categoryId", source = "member.category.id")
    ExpenseResponse toResponse(ExpenseEntity member);

    @Mapping(target = "owner", source = "owner.username")
    ExpenseSheetResponse toResponse(ExpenseSheetEntity expenseSheet);

    ExpenseCategoryResponse toResponse(ExpenseCategoryEntity category);

    @Mapping(target = "id", ignore = true)
    ExpenseCategoryEntity toEntity(ExpenseCategoryRequest request);
}
