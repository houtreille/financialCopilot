import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ExpenseSheet, ExpenseSheetRequest } from './expense-sheet';

@Injectable({ providedIn: 'root' })
export class ExpenseSheetService {
  private readonly http = inject(HttpClient);

  findAllByOwner(owner: string): Observable<ExpenseSheet[]> {
    return this.http.get<ExpenseSheet[]>('/api/expense-sheets', { params: { owner } });
  }

  create(request: ExpenseSheetRequest): Observable<ExpenseSheet> {
    return this.http.post<ExpenseSheet>('/api/expense-sheet', request);
  }

  findById(id: number): Observable<ExpenseSheet> {
    return this.http.get<ExpenseSheet>(`/api/expense-sheets/${id}`);
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`/api/expense-sheets/${id}`, { responseType: 'text' });
  }
}
