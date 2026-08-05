import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {
  CellClickedEvent,
  CellStyleModule,
  CellValueChangedEvent,
  ClientSideRowModelModule,
  ColDef,
  DateEditorModule,
  ModuleRegistry,
  NumberEditorModule,
  RowClassParams,
  RowStyle,
  RowStyleModule,
  SelectEditorModule,
  TextEditorModule,
  themeQuartz
} from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { ExpenseCategory } from '../expense-category';
import { ExpenseCategoryService } from '../expense-category.service';
import { EXPENSE_TYPES, Expense, ExpenseRequest, ExpenseSheet, ExpenseType, MONTHS } from '../expense-sheet';
import { ExpenseSheetService } from '../expense-sheet.service';
import { ExpenseService } from '../expense.service';
import { NavMenu } from '../nav-menu/nav-menu';

ModuleRegistry.registerModules([
  ClientSideRowModelModule,
  TextEditorModule,
  NumberEditorModule,
  SelectEditorModule,
  DateEditorModule,
  CellStyleModule,
  RowStyleModule
]);

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
  private readonly expenseCategoryService = inject(ExpenseCategoryService);

  protected readonly expenseSheet = signal<ExpenseSheet | null>(null);
  protected readonly categories = signal<ExpenseCategory[]>([]);
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
  protected newStartDate = '';
  protected newEndDate = '';
  protected newDescription = '';
  protected newCategoryId: number | null = null;

  protected get columnDefs(): ColDef<Expense>[] {
    return [
      { field: 'label', headerName: 'Label', editable: true },
      { field: 'amount', headerName: 'Amount', editable: true, cellEditor: 'agNumberCellEditor' },
      {
        field: 'month',
        headerName: 'Month',
        editable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: { values: MONTHS }
      },
      {
        field: 'expenseType',
        headerName: 'Type',
        editable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: { values: EXPENSE_TYPES }
      },
      {
        field: 'categoryId',
        headerName: 'Category',
        editable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: { values: this.categories().map((category) => category.id) },
        valueFormatter: (params) => this.categoryLabel(params.value)
      },
      { field: 'startDate', headerName: 'Start date', editable: true, cellEditor: 'agDateStringCellEditor' },
      { field: 'endDate', headerName: 'End date', editable: true, cellEditor: 'agDateStringCellEditor' },
      { field: 'description', headerName: 'Description', editable: true },
      {
        colId: 'delete',
        headerName: '',
        width: 60,
        cellRenderer: () => '🗑️',
        cellStyle: { cursor: 'pointer', textAlign: 'center' }
      }
    ];
  }

  private categoryLabel(categoryId: number | null): string {
    if (categoryId == null) {
      return '';
    }
    return this.categories().find((category) => category.id === categoryId)?.label ?? '';
  }

  private categoryColor(categoryId: number | null): string | null {
    if (categoryId == null) {
      return null;
    }
    return this.categories().find((category) => category.id === categoryId)?.color ?? null;
  }

  protected getRowStyle = (params: RowClassParams<Expense>): RowStyle | undefined => {
    const color = this.categoryColor(params.data?.categoryId ?? null);
    return color ? { backgroundColor: pastelize(color) } : undefined;
  };

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

    this.expenseCategoryService.findAll().subscribe({
      next: (categories) => this.categories.set(categories),
      error: () => this.error.set('Failed to load expense categories.')
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

  protected onCellValueChanged(event: CellValueChangedEvent<Expense>): void {
    if (!event.data) {
      return;
    }

    const sheet = this.expenseSheet();
    if (!sheet) {
      return;
    }

    const request: ExpenseRequest = {
      expenseSheetId: sheet.id,
      amount: event.data.amount,
      label: event.data.label,
      month: event.data.month,
      expenseType: event.data.expenseType,
      startDate: event.data.startDate || null,
      endDate: event.data.endDate || null,
      description: event.data.description || null,
      categoryId: event.data.categoryId
    };

    this.expenseService.update(event.data.id, request).subscribe({
      next: (updated) =>
        this.expenseSheet.set({
          ...sheet,
          expenses: sheet.expenses.map((expense) => (expense.id === updated.id ? updated : expense))
        }),
      error: () => this.error.set('Failed to update expense.')
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
        expenseType: this.newExpenseType,
        startDate: this.newStartDate || null,
        endDate: this.newEndDate || null,
        description: this.newDescription || null,
        categoryId: this.newCategoryId
      })
      .subscribe({
        next: (expense) => {
          this.expenseSheet.set({ ...sheet, expenses: [...sheet.expenses, expense] });
          this.newLabel = '';
          this.newAmount = null;
          this.newMonth = MONTHS[0];
          this.newExpenseType = EXPENSE_TYPES[0];
          this.newStartDate = '';
          this.newEndDate = '';
          this.newDescription = '';
          this.newCategoryId = null;
        },
        error: () => this.createError.set('Failed to create expense.')
      });
  }
}

function pastelize(hexColor: string, whiteMix = 0.75): string {
  const match = /^#?([0-9a-f]{6})$/i.exec(hexColor.trim());
  if (!match) {
    return hexColor;
  }

  const value = parseInt(match[1], 16);
  const r = (value >> 16) & 0xff;
  const g = (value >> 8) & 0xff;
  const b = value & 0xff;

  const lighten = (channel: number) => Math.round(channel + (255 - channel) * whiteMix);

  return `rgb(${lighten(r)}, ${lighten(g)}, ${lighten(b)})`;
}
