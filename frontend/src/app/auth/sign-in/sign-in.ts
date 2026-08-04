import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-sign-in',
  imports: [FormsModule, RouterLink],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css'
})
export class SignIn {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected username = '';
  protected password = '';
  protected readonly error = signal<string | null>(null);

  submit(): void {
    this.error.set(null);

    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: (user) => this.router.navigate([user.isAdmin ? '/dashboard' : '/expense-sheets']),
      error: () => this.error.set('Invalid username or password.')
    });
  }
}
