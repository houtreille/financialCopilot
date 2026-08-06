import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CashflowCategory, CashflowCategoryRequest } from './cashflow-category';

@Injectable({ providedIn: 'root' })
export class CashflowCategoryService {
  private readonly http = inject(HttpClient);

  findAll(): Observable<CashflowCategory[]> {
    return this.http.get<CashflowCategory[]>('/api/cashflow-categories');
  }

  create(request: CashflowCategoryRequest): Observable<CashflowCategory> {
    return this.http.post<CashflowCategory>('/api/cashflow-category', request);
  }
}
