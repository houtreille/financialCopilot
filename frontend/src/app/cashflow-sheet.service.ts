import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CashflowSheet, CashflowSheetRequest } from './cashflow-sheet';

@Injectable({ providedIn: 'root' })
export class CashflowSheetService {
  private readonly http = inject(HttpClient);

  findAllByOwner(owner: string): Observable<CashflowSheet[]> {
    return this.http.get<CashflowSheet[]>('/api/cashflow-sheets', { params: { owner } });
  }

  create(request: CashflowSheetRequest): Observable<CashflowSheet> {
    return this.http.post<CashflowSheet>('/api/cashflow-sheet', request);
  }

  findById(id: number): Observable<CashflowSheet> {
    return this.http.get<CashflowSheet>(`/api/cashflow-sheets/${id}`);
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`/api/cashflow-sheets/${id}`, { responseType: 'text' });
  }
}
