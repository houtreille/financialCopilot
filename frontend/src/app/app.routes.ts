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
    path: 'cashflow-sheets',
    canActivate: [authenticatedGuard],
    loadComponent: () => import('./cashflow-sheet/cashflow-sheets').then((m) => m.CashflowSheets)
  },
  {
    path: 'cashflow-sheets/:id',
    canActivate: [authenticatedGuard],
    loadComponent: () => import('./cashflow-sheet/cashflow-sheet-viewer').then((m) => m.CashflowSheetViewer)
  },
  {
    path: 'cashflow-categories',
    canActivate: [authGuard],
    loadComponent: () => import('./cashflow-category/cashflow-categories').then((m) => m.CashflowCategories)
  },
  { path: '**', redirectTo: 'sign-in' }
];
