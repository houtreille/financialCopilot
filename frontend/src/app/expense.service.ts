import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Expense, ExpenseRequest } from './expense-sheet';

@Injectable({ providedIn: 'root' })
export class ExpenseService {
  private readonly http = inject(HttpClient);

  create(request: ExpenseRequest): Observable<Expense> {
    return this.http.post<Expense>('/api/expense', request);
  }

  update(id: number, request: ExpenseRequest): Observable<Expense> {
    return this.http.put<Expense>(`/api/expense/${id}`, request);
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`/api/expense/${id}`, { responseType: 'text' });
  }
}
