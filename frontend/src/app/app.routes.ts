import { Routes } from '@angular/router';
import { authGuard } from './auth/auth.guard';
import { authenticatedGuard } from './auth/authenticated.guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'sign-in' },
  {
    path: 'sign-in',
    loadComponent: () => import('./auth/sign-in/sign-in').then((m) => m.SignIn)
  },
  {
    path: 'sign-up',
    loadComponent: () => import('./auth/sign-up/sign-up').then((m) => m.SignUp)
  },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () => import('./dashboard/dashboard').then((m) => m.Dashboard)
  },
  {
    path: 'expense-sheets',
    canActivate: [authenticatedGuard],
    loadComponent: () => import('./expense-sheet/expense-sheets').then((m) => m.ExpenseSheets)
  },
  { path: '**', redirectTo: 'sign-in' }
];
