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

export const CASHFLOW_TYPES = ['MONTHLY', 'YEARLY'] as const;

export type CashflowType = (typeof CASHFLOW_TYPES)[number];

export const CASHFLOW_SHEET_TYPES = ['MONTHLY', 'YEARLY'] as const;

export type CashflowSheetType = (typeof CASHFLOW_SHEET_TYPES)[number];

export const CASHFLOW_DIRECTIONS = ['OUTFLOW', 'INFLOW'] as const;

export type CashflowDirection = (typeof CASHFLOW_DIRECTIONS)[number];

export interface Cashflow {
  id: number;
  cashflowSheetId: number;
  amount: number;
  signedAmountPerMonth: number;
  label: string;
  month: string;
  cashflowVersion: number;
  cashflowVersionId: number;
  cashflowType: CashflowType;
  direction: CashflowDirection;
  startDate: string | null;
  endDate: string | null;
  description: string | null;
  categoryId: number | null;
}

export interface CashflowRequest {
  cashflowSheetId: number;
  amount: number;
  label: string;
  month: string;
  cashflowType: CashflowType;
  direction: CashflowDirection;
  startDate: string | null;
  endDate: string | null;
  description: string | null;
  categoryId: number | null;
}

export interface CashflowSheet {
  id: number;
  sheetName: string;
  owner: string;
  cashflows: Cashflow[];
  type: CashflowSheetType;
}

export interface CashflowSheetRequest {
  sheetName: string;
  owner: string;
  type: CashflowSheetType;
}
