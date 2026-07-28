import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';

export interface CurrentUser {
  id: number;
  username: string;
  isAdmin: boolean;
}

export interface SignUpRequest {
  username: string;
  password: string;
  dateOfBirth: string;
  countryOfResidence: string;
  countryOfEmployment: string;
  averageMonthlySalary: number;
  currentCash: number;
}

export interface LoginRequest {
  username: string;
  password: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);

  readonly currentUser = signal<CurrentUser | null>(null);

  signUp(request: SignUpRequest): Observable<CurrentUser> {
    return this.http.post<CurrentUser>('/api/auth/signup', request, { withCredentials: true });
  }

  login(request: LoginRequest): Observable<CurrentUser> {
    return this.http
      .post<CurrentUser>('/api/auth/login', request, { withCredentials: true })
      .pipe(tap((user) => this.currentUser.set(user)));
  }

  logout(): Observable<void> {
    return this.http
      .post<void>('/api/auth/logout', {}, { withCredentials: true })
      .pipe(tap(() => this.currentUser.set(null)));
  }

  me(): Observable<CurrentUser> {
    return this.http
      .get<CurrentUser>('/api/auth/me', { withCredentials: true })
      .pipe(tap((user) => this.currentUser.set(user)));
  }
}
