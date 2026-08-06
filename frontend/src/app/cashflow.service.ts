import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Cashflow, CashflowRequest } from './cashflow-sheet';

@Injectable({ providedIn: 'root' })
export class CashflowService {
  private readonly http = inject(HttpClient);

  create(request: CashflowRequest): Observable<Cashflow> {
    return this.http.post<Cashflow>('/api/cashflow', request);
  }

  update(id: number, request: CashflowRequest): Observable<Cashflow> {
    return this.http.put<Cashflow>(`/api/cashflow/${id}`, request);
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`/api/cashflow/${id}`, { responseType: 'text' });
  }
}
