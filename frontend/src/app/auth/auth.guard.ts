import { inject } from '@angular/core';
import { Router, type CanActivateFn } from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const cachedUser = authService.currentUser();
  if (cachedUser) {
    return cachedUser.isAdmin ? true : router.createUrlTree(['/sign-in']);
  }

  return authService.me().pipe(
    map((user) => (user.isAdmin ? true : router.createUrlTree(['/sign-in']))),
    catchError(() => of(router.createUrlTree(['/sign-in'])))
  );
};
