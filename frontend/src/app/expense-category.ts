export interface ExpenseCategory {
  id: number;
  label: string;
  color: string | null;
}

export interface ExpenseCategoryRequest {
  label: string;
  color: string | null;
}
