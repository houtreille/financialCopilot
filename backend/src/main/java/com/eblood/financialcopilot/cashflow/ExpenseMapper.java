package com.eblood.financialcopilot.cashflow;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    @Mapping(target = "id", ignore = true)
    ExpenseEntity toEntity(ExpenseRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    ExpenseSheetEntity toEntity(ExpenseSheetRequest request);

    @Mapping(target = "expenseSheetId", source = "member.expenseSheet.id")
    ExpenseResponse toResponse(ExpenseEntity member);

    @Mapping(target = "owner", source = "owner.username")
    ExpenseSheetResponse toResponse(ExpenseSheetEntity expenseSheet);

}
