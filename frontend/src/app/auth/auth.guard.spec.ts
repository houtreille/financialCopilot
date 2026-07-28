import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Router, provideRouter } from '@angular/router';
import { firstValueFrom, isObservable } from 'rxjs';
import { authGuard } from './auth.guard';

describe('authGuard', () => {
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])]
    });

    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('allows navigation when the session is valid and the user is an admin', async () => {
    const result = TestBed.runInInjectionContext(() => authGuard({} as never, {} as never));
    if (!isObservable(result)) {
      throw new Error('expected an observable');
    }
    const allowed = firstValueFrom(result);

    httpMock.expectOne('/api/auth/me').flush({ id: 1, username: 'admin', isAdmin: true });

    expect(await allowed).toBe(true);
  });

  it('redirects to sign-in when the session is valid but the user is not an admin', async () => {
    const router = TestBed.inject(Router);
    const result = TestBed.runInInjectionContext(() => authGuard({} as never, {} as never));
    if (!isObservable(result)) {
      throw new Error('expected an observable');
    }
    const outcome = firstValueFrom(result);

    httpMock.expectOne('/api/auth/me').flush({ id: 2, username: 'jane', isAdmin: false });

    expect(await outcome).toEqual(router.createUrlTree(['/sign-in']));
  });

  it('redirects to sign-in when there is no session', async () => {
    const router = TestBed.inject(Router);
    const result = TestBed.runInInjectionContext(() => authGuard({} as never, {} as never));
    if (!isObservable(result)) {
      throw new Error('expected an observable');
    }
    const outcome = firstValueFrom(result);

    httpMock.expectOne('/api/auth/me').flush('Unauthorized', { status: 401, statusText: 'Unauthorized' });

    expect(await outcome).toEqual(router.createUrlTree(['/sign-in']));
  });
});
