export interface CashflowCategory {
  id: number;
  label: string;
  color: string | null;
}

export interface CashflowCategoryRequest {
  label: string;
  color: string | null;
}
