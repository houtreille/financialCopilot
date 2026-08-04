export const MONTHS = [
  'JANUARY',
  'FEBRUARY',
  'MARCH',
  'APRIL',
  'MAY',
  'JUNE',
  'JULY',
  'AUGUST',
  'SEPTEMBER',
  'OCTOBER',
  'NOVEMBER',
  'DECEMBER'
] as const;

export const EXPENSE_TYPES = ['MONTHLY', 'YEARLY'] as const;

export type ExpenseType = (typeof EXPENSE_TYPES)[number];

export interface Expense {
  id: number;
  expenseSheetId: number;
  amount: number;
  label: string;
  month: string;
  expenseVersion: number;
  expenseVersionId: number;
  expenseType: ExpenseType;
}

export interface ExpenseRequest {
  expenseSheetId: number;
  amount: number;
  label: string;
  month: string;
  expenseType: ExpenseType;
}

export interface ExpenseSheet {
  id: number;
  sheetName: string;
  owner: string;
  expenses: Expense[];
}

export interface ExpenseSheetRequest {
  sheetName: string;
  owner: string;
}
