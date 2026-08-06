import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CellClickedEvent, ClientSideRowModelModule, ColDef, ModuleRegistry, themeQuartz } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { AuthService } from '../auth/auth.service';
import { CASHFLOW_SHEET_TYPES, CashflowSheet, CashflowSheetType } from '../cashflow-sheet';
import { CashflowSheetService } from '../cashflow-sheet.service';
import { NavMenu } from '../nav-menu/nav-menu';

ModuleRegistry.registerModules([ClientSideRowModelModule]);

@Component({
  selector: 'app-cashflow-sheets',
  imports: [AgGridAngular, NavMenu, FormsModule],
  templateUrl: './cashflow-sheets.html',
  styleUrl: './cashflow-sheets.css'
})
export class CashflowSheets implements OnInit {
  private readonly cashflowSheetService = inject(CashflowSheetService);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly cashflowSheets = signal<CashflowSheet[]>([]);
  protected readonly error = signal<string | null>(null);
  protected readonly createError = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected newSheetName = '';
  protected newType: CashflowSheetType = CASHFLOW_SHEET_TYPES[0];

  protected readonly cashflowSheetTypes = CASHFLOW_SHEET_TYPES;

  protected readonly columnDefs: ColDef<CashflowSheet>[] = [
    { field: 'sheetName', headerName: 'Sheet name' },
    { field: 'owner', headerName: 'Owner' },
    { field: 'type', headerName: 'Type' },
    { headerName: 'Cashflows', valueGetter: (params) => params.data?.cashflows?.length ?? 0 },
    {
      colId: 'delete',
      headerName: '',
      width: 60,
      cellRenderer: () => '🗑️',
      cellStyle: { cursor: 'pointer', textAlign: 'center' }
    }
  ];

  protected onCellClicked(event: CellClickedEvent<CashflowSheet>): void {
    if (!event.data) {
      return;
    }

    if (event.column.getColId() === 'delete') {
      this.cashflowSheetService.delete(event.data.id).subscribe({
        next: () =>
          this.cashflowSheets.set(this.cashflowSheets().filter((sheet) => sheet.id !== event.data!.id)),
        error: () => this.error.set('Failed to delete cashflow sheet.')
      });
      return;
    }

    this.router.navigate(['/cashflow-sheets', event.data.id]);
  }

  ngOnInit(): void {
    const owner = this.authService.currentUser()?.username;
    if (!owner) {
      this.error.set('No user connected.');
      return;
    }

    this.cashflowSheetService.findAllByOwner(owner).subscribe({
      next: (sheets) => this.cashflowSheets.set(sheets),
      error: () => this.error.set('Failed to load cashflow sheets.')
    });
  }

  protected addCashflowSheet(): void {
    const owner = this.authService.currentUser()?.username;
    if (!owner || !this.newSheetName) {
      return;
    }

    this.createError.set(null);

    this.cashflowSheetService.create({ sheetName: this.newSheetName, owner, type: this.newType }).subscribe({
      next: (sheet) => {
        this.cashflowSheets.set([...this.cashflowSheets(), { ...sheet, cashflows: sheet.cashflows ?? [] }]);
        this.newSheetName = '';
        this.newType = CASHFLOW_SHEET_TYPES[0];
      },
      error: () => this.createError.set('Failed to create cashflow sheet.')
    });
  }
}
