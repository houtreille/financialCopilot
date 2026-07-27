import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService, SignUpRequest } from '../auth.service';

@Component({
  selector: 'app-sign-up',
  imports: [FormsModule, RouterLink],
  templateUrl: './sign-up.html',
  styleUrl: './sign-up.css'
})
export class SignUp {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly form: SignUpRequest = {
    username: '',
    password: '',
    dateOfBirth: '',
    countryOfResidence: '',
    countryOfEmployment: '',
    averageMonthlySalary: 0,
    currentCash: 0
  };

  protected readonly error = signal<string | null>(null);

  submit(): void {
    this.error.set(null);

    this.authService.signUp(this.form).subscribe({
      next: () => this.router.navigate(['/sign-in']),
      error: (response) =>
        this.error.set(
          response.status === 409 ? 'This username is already taken.' : 'Could not create the account.'
        )
    });
  }
}
