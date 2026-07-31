import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ExpenseSheet } from './expense-sheet';

@Injectable({ providedIn: 'root' })
export class ExpenseSheetService {
  private readonly http = inject(HttpClient);

  findAllByOwner(owner: string): Observable<ExpenseSheet[]> {
    return this.http.get<ExpenseSheet[]>('/api/expense-sheets', { params: { owner } });
  }
}
