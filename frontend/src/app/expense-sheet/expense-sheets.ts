import { Component, OnInit, inject, signal } from '@angular/core';
import { ClientSideRowModelModule, ColDef, ModuleRegistry, themeQuartz } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { AuthService } from '../auth/auth.service';
import { ExpenseSheet } from '../expense-sheet';
import { ExpenseSheetService } from '../expense-sheet.service';
import { NavMenu } from '../nav-menu/nav-menu';

ModuleRegistry.registerModules([ClientSideRowModelModule]);

@Component({
  selector: 'app-expense-sheets',
  imports: [AgGridAngular, NavMenu],
  templateUrl: './expense-sheets.html',
  styleUrl: './expense-sheets.css'
})
export class ExpenseSheets implements OnInit {
  private readonly expenseSheetService = inject(ExpenseSheetService);
  private readonly authService = inject(AuthService);

  protected readonly expenseSheets = signal<ExpenseSheet[]>([]);
  protected readonly error = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected readonly columnDefs: ColDef<ExpenseSheet>[] = [
    { field: 'sheetName', headerName: 'Sheet name' },
    { field: 'owner', headerName: 'Owner' },
    { headerName: 'Expenses', valueGetter: (params) => params.data?.expenses.length ?? 0 }
  ];

  ngOnInit(): void {
    const owner = this.authService.currentUser()?.username;
    if (!owner) {
      this.error.set('No user connected.');
      return;
    }

    this.expenseSheetService.findAllByOwner(owner).subscribe({
      next: (sheets) => this.expenseSheets.set(sheets),
      error: () => this.error.set('Failed to load expense sheets.')
    });
  }
}
