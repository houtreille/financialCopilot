import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CellStyleModule, ClientSideRowModelModule, ColDef, ModuleRegistry, themeQuartz } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { CashflowCategory } from '../cashflow-category';
import { CashflowCategoryService } from '../cashflow-category.service';
import { NavMenu } from '../nav-menu/nav-menu';

ModuleRegistry.registerModules([ClientSideRowModelModule, CellStyleModule]);

@Component({
  selector: 'app-cashflow-categories',
  imports: [AgGridAngular, NavMenu, FormsModule],
  templateUrl: './cashflow-categories.html',
  styleUrl: './cashflow-categories.css'
})
export class CashflowCategories implements OnInit {
  private readonly cashflowCategoryService = inject(CashflowCategoryService);

  protected readonly categories = signal<CashflowCategory[]>([]);
  protected readonly error = signal<string | null>(null);
  protected readonly createError = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected newLabel = '';
  protected newColor = '#1a7f37';

  protected readonly columnDefs: ColDef<CashflowCategory>[] = [
    { field: 'label', headerName: 'Label' },
    {
      field: 'color',
      headerName: 'Color',
      cellStyle: (params) => ({ borderLeft: `8px solid ${params.value ?? 'transparent'}` })
    }
  ];

  ngOnInit(): void {
    this.cashflowCategoryService.findAll().subscribe({
      next: (categories) => this.categories.set(categories),
      error: () => this.error.set('Failed to load cashflow categories.')
    });
  }

  protected addCategory(): void {
    if (!this.newLabel) {
      return;
    }

    this.createError.set(null);

    this.cashflowCategoryService.create({ label: this.newLabel, color: this.newColor }).subscribe({
      next: (category) => {
        this.categories.set([...this.categories(), category]);
        this.newLabel = '';
        this.newColor = '#1a7f37';
      },
      error: () => this.createError.set('Failed to create cashflow category.')
    });
  }
}
