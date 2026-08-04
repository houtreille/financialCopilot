import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CellClickedEvent, ClientSideRowModelModule, ColDef, ModuleRegistry, themeQuartz } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { AuthService } from '../auth/auth.service';
import { ExpenseSheet } from '../expense-sheet';
import { ExpenseSheetService } from '../expense-sheet.service';
import { NavMenu } from '../nav-menu/nav-menu';

ModuleRegistry.registerModules([ClientSideRowModelModule]);

@Component({
  selector: 'app-expense-sheets',
  imports: [AgGridAngular, NavMenu, FormsModule],
  templateUrl: './expense-sheets.html',
  styleUrl: './expense-sheets.css'
})
export class ExpenseSheets implements OnInit {
  private readonly expenseSheetService = inject(ExpenseSheetService);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly expenseSheets = signal<ExpenseSheet[]>([]);
  protected readonly error = signal<string | null>(null);
  protected readonly createError = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected newSheetName = '';

  protected readonly columnDefs: ColDef<ExpenseSheet>[] = [
    { field: 'sheetName', headerName: 'Sheet name' },
    { field: 'owner', headerName: 'Owner' },
    { headerName: 'Expenses', valueGetter: (params) => params.data?.expenses?.length ?? 0 },
    {
      colId: 'delete',
      headerName: '',
      width: 60,
      cellRenderer: () => '🗑️',
      cellStyle: { cursor: 'pointer', textAlign: 'center' }
    }
  ];

  protected onCellClicked(event: CellClickedEvent<ExpenseSheet>): void {
    if (!event.data) {
      return;
    }

    if (event.column.getColId() === 'delete') {
      this.expenseSheetService.delete(event.data.id).subscribe({
        next: () =>
          this.expenseSheets.set(this.expenseSheets().filter((sheet) => sheet.id !== event.data!.id)),
        error: () => this.error.set('Failed to delete expense sheet.')
      });
      return;
    }

    this.router.navigate(['/expense-sheets', event.data.id]);
  }

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

  protected addExpenseSheet(): void {
    const owner = this.authService.currentUser()?.username;
    if (!owner || !this.newSheetName) {
      return;
    }

    this.createError.set(null);

    this.expenseSheetService.create({ sheetName: this.newSheetName, owner }).subscribe({
      next: (sheet) => {
        this.expenseSheets.set([...this.expenseSheets(), { ...sheet, expenses: sheet.expenses ?? [] }]);
        this.newSheetName = '';
      },
      error: () => this.createError.set('Failed to create expense sheet.')
    });
  }
}
