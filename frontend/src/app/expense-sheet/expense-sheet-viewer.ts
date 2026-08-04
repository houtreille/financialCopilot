import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CellClickedEvent, ClientSideRowModelModule, ColDef, ModuleRegistry, themeQuartz } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { EXPENSE_TYPES, Expense, ExpenseSheet, ExpenseType, MONTHS } from '../expense-sheet';
import { ExpenseSheetService } from '../expense-sheet.service';
import { ExpenseService } from '../expense.service';
import { NavMenu } from '../nav-menu/nav-menu';

ModuleRegistry.registerModules([ClientSideRowModelModule]);

@Component({
  selector: 'app-expense-sheet-viewer',
  imports: [AgGridAngular, NavMenu, FormsModule],
  templateUrl: './expense-sheet-viewer.html',
  styleUrl: './expense-sheet-viewer.css'
})
export class ExpenseSheetViewer implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly expenseSheetService = inject(ExpenseSheetService);
  private readonly expenseService = inject(ExpenseService);

  protected readonly expenseSheet = signal<ExpenseSheet | null>(null);
  protected readonly error = signal<string | null>(null);
  protected readonly createError = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected readonly totalAmount = computed(
    () => this.expenseSheet()?.expenses.reduce((total, expense) => total + expense.amount, 0) ?? 0
  );

  protected readonly months = MONTHS;
  protected readonly expenseTypes = EXPENSE_TYPES;

  protected newLabel = '';
  protected newAmount: number | null = null;
  protected newMonth: string = MONTHS[0];
  protected newExpenseType: ExpenseType = EXPENSE_TYPES[0];

  protected readonly columnDefs: ColDef<Expense>[] = [
    { field: 'label', headerName: 'Label' },
    { field: 'amount', headerName: 'Amount' },
    { field: 'month', headerName: 'Month' },
    { field: 'expenseType', headerName: 'Type' },
    {
      colId: 'delete',
      headerName: '',
      width: 60,
      cellRenderer: () => '🗑️',
      cellStyle: { cursor: 'pointer', textAlign: 'center' }
    }
  ];

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error.set('Invalid expense sheet id.');
      return;
    }

    this.expenseSheetService.findById(id).subscribe({
      next: (sheet) => this.expenseSheet.set(sheet),
      error: () => this.error.set('Failed to load expense sheet.')
    });
  }

  protected onCellClicked(event: CellClickedEvent<Expense>): void {
    if (event.column.getColId() !== 'delete' || !event.data) {
      return;
    }

    const sheet = this.expenseSheet();
    if (!sheet) {
      return;
    }

    this.expenseService.delete(event.data.id).subscribe({
      next: () =>
        this.expenseSheet.set({
          ...sheet,
          expenses: sheet.expenses.filter((expense) => expense.id !== event.data!.id)
        }),
      error: () => this.error.set('Failed to delete expense.')
    });
  }

  protected addExpense(): void {
    const sheet = this.expenseSheet();
    if (!sheet || !this.newLabel || this.newAmount === null) {
      return;
    }

    this.createError.set(null);

    this.expenseService
      .create({
        expenseSheetId: sheet.id,
        amount: this.newAmount,
        label: this.newLabel,
        month: this.newMonth,
        expenseType: this.newExpenseType
      })
      .subscribe({
        next: (expense) => {
          this.expenseSheet.set({ ...sheet, expenses: [...sheet.expenses, expense] });
          this.newLabel = '';
          this.newAmount = null;
          this.newMonth = MONTHS[0];
          this.newExpenseType = EXPENSE_TYPES[0];
        },
        error: () => this.createError.set('Failed to create expense.')
      });
  }
}
