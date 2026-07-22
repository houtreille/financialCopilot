import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HouseholdMember } from './household-member';

@Injectable({ providedIn: 'root' })
export class HouseholdMemberService {
  private readonly http = inject(HttpClient);

  findAll(): Observable<HouseholdMember[]> {
    return this.http.get<HouseholdMember[]>('/api/household-member');
  }
}
