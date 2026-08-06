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
import { CashflowCategory } from '../cashflow-category';
import { CashflowCategoryService } from '../cashflow-category.service';
import {
  CASHFLOW_DIRECTIONS,
  CASHFLOW_TYPES,
  Cashflow,
  CashflowDirection,
  CashflowRequest,
  CashflowSheet,
  CashflowType,
  MONTHS
} from '../cashflow-sheet';
import { CashflowSheetService } from '../cashflow-sheet.service';
import { CashflowService } from '../cashflow.service';
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
  selector: 'app-cashflow-sheet-viewer',
  imports: [AgGridAngular, NavMenu, FormsModule],
  templateUrl: './cashflow-sheet-viewer.html',
  styleUrl: './cashflow-sheet-viewer.css'
})
export class CashflowSheetViewer implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly cashflowSheetService = inject(CashflowSheetService);
  private readonly cashflowService = inject(CashflowService);
  private readonly cashflowCategoryService = inject(CashflowCategoryService);

  protected readonly cashflowSheet = signal<CashflowSheet | null>(null);
  protected readonly categories = signal<CashflowCategory[]>([]);
  protected readonly error = signal<string | null>(null);
  protected readonly createError = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected readonly totalAmount = computed(
    () => this.cashflowSheet()?.cashflows.reduce((total, cashflow) => total + cashflow.signedAmountPerMonth, 0) ?? 0
  );

  protected readonly months = MONTHS;
  protected readonly cashflowTypes = CASHFLOW_TYPES;
  protected readonly cashflowDirections = CASHFLOW_DIRECTIONS;

  protected newLabel = '';
  protected newAmount: number | null = null;
  protected newMonth: string = MONTHS[0];
  protected newCashflowType: CashflowType = CASHFLOW_TYPES[0];
  protected newDirection: CashflowDirection = CASHFLOW_DIRECTIONS[0];
  protected newStartDate = '';
  protected newEndDate = '';
  protected newDescription = '';
  protected newCategoryId: number | null = null;

  protected get columnDefs(): ColDef<Cashflow>[] {
    return [
      { field: 'label', headerName: 'Label', editable: true },
      { field: 'amount', headerName: 'Amount', editable: true, cellEditor: 'agNumberCellEditor' },
      { field: 'signedAmountPerMonth', headerName: 'Signed / month', editable: false, width: 130 },
      {
        field: 'month',
        headerName: 'Month',
        editable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: { values: MONTHS },
        valueFormatter: (params) => (params.value ? params.value.slice(0, 3) : ''),
        width: 90
      },
      {
        field: 'cashflowType',
        headerName: 'Type',
        editable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: { values: CASHFLOW_TYPES },
        valueFormatter: (params) => (params.value ? params.value.charAt(0) : ''),
        width: 70
      },
      {
        field: 'direction',
        headerName: 'Direction',
        editable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: { values: CASHFLOW_DIRECTIONS },
        valueFormatter: (params) => (params.value === 'INFLOW' ? '+' : '-'),
        cellStyle: (params) => ({
          color: params.value === 'INFLOW' ? '#1a7f37' : '#b42318',
          fontWeight: 'bold',
          textAlign: 'center'
        }),
        width: 70
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

  protected getRowStyle = (params: RowClassParams<Cashflow>): RowStyle | undefined => {
    const color = this.categoryColor(params.data?.categoryId ?? null);
    return color ? { backgroundColor: pastelize(color) } : undefined;
  };

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error.set('Invalid cashflow sheet id.');
      return;
    }

    this.cashflowSheetService.findById(id).subscribe({
      next: (sheet) => this.cashflowSheet.set(sheet),
      error: () => this.error.set('Failed to load cashflow sheet.')
    });

    this.cashflowCategoryService.findAll().subscribe({
      next: (categories) => this.categories.set(categories),
      error: () => this.error.set('Failed to load cashflow categories.')
    });
  }

  protected onCellClicked(event: CellClickedEvent<Cashflow>): void {
    if (event.column.getColId() !== 'delete' || !event.data) {
      return;
    }

    const sheet = this.cashflowSheet();
    if (!sheet) {
      return;
    }

    this.cashflowService.delete(event.data.id).subscribe({
      next: () =>
        this.cashflowSheet.set({
          ...sheet,
          cashflows: sheet.cashflows.filter((cashflow) => cashflow.id !== event.data!.id)
        }),
      error: () => this.error.set('Failed to delete cashflow.')
    });
  }

  protected onCellValueChanged(event: CellValueChangedEvent<Cashflow>): void {
    if (!event.data) {
      return;
    }

    const sheet = this.cashflowSheet();
    if (!sheet) {
      return;
    }

    const request: CashflowRequest = {
      cashflowSheetId: sheet.id,
      amount: event.data.amount,
      label: event.data.label,
      month: event.data.month,
      cashflowType: event.data.cashflowType,
      direction: event.data.direction,
      startDate: event.data.startDate || null,
      endDate: event.data.endDate || null,
      description: event.data.description || null,
      categoryId: event.data.categoryId
    };

    this.cashflowService.update(event.data.id, request).subscribe({
      next: (updated) =>
        this.cashflowSheet.set({
          ...sheet,
          cashflows: sheet.cashflows.map((cashflow) => (cashflow.id === updated.id ? updated : cashflow))
        }),
      error: () => this.error.set('Failed to update cashflow.')
    });
  }

  protected addCashflow(): void {
    const sheet = this.cashflowSheet();
    if (!sheet || !this.newLabel || this.newAmount === null) {
      return;
    }

    this.createError.set(null);

    this.cashflowService
      .create({
        cashflowSheetId: sheet.id,
        amount: this.newAmount,
        label: this.newLabel,
        month: this.newMonth,
        cashflowType: this.newCashflowType,
        direction: this.newDirection,
        startDate: this.newStartDate || null,
        endDate: this.newEndDate || null,
        description: this.newDescription || null,
        categoryId: this.newCategoryId
      })
      .subscribe({
        next: (cashflow) => {
          this.cashflowSheet.set({ ...sheet, cashflows: [...sheet.cashflows, cashflow] });
          this.newLabel = '';
          this.newAmount = null;
          this.newMonth = MONTHS[0];
          this.newCashflowType = CASHFLOW_TYPES[0];
          this.newDirection = CASHFLOW_DIRECTIONS[0];
          this.newStartDate = '';
          this.newEndDate = '';
          this.newDescription = '';
          this.newCategoryId = null;
        },
        error: () => this.createError.set('Failed to create cashflow.')
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
