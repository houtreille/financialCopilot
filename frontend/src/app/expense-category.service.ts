import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ExpenseCategory, ExpenseCategoryRequest } from './expense-category';

@Injectable({ providedIn: 'root' })
export class ExpenseCategoryService {
  private readonly http = inject(HttpClient);

  findAll(): Observable<ExpenseCategory[]> {
    return this.http.get<ExpenseCategory[]>('/api/expense-categories');
  }

  create(request: ExpenseCategoryRequest): Observable<ExpenseCategory> {
    return this.http.post<ExpenseCategory>('/api/expense-category', request);
  }
}
