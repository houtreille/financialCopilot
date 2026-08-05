import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CellStyleModule, ClientSideRowModelModule, ColDef, ModuleRegistry, themeQuartz } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { ExpenseCategory } from '../expense-category';
import { ExpenseCategoryService } from '../expense-category.service';
import { NavMenu } from '../nav-menu/nav-menu';

ModuleRegistry.registerModules([ClientSideRowModelModule, CellStyleModule]);

@Component({
  selector: 'app-expense-categories',
  imports: [AgGridAngular, NavMenu, FormsModule],
  templateUrl: './expense-categories.html',
  styleUrl: './expense-categories.css'
})
export class ExpenseCategories implements OnInit {
  private readonly expenseCategoryService = inject(ExpenseCategoryService);

  protected readonly categories = signal<ExpenseCategory[]>([]);
  protected readonly error = signal<string | null>(null);
  protected readonly createError = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected newLabel = '';
  protected newColor = '#1a7f37';

  protected readonly columnDefs: ColDef<ExpenseCategory>[] = [
    { field: 'label', headerName: 'Label' },
    {
      field: 'color',
      headerName: 'Color',
      cellStyle: (params) => ({ borderLeft: `8px solid ${params.value ?? 'transparent'}` })
    }
  ];

  ngOnInit(): void {
    this.expenseCategoryService.findAll().subscribe({
      next: (categories) => this.categories.set(categories),
      error: () => this.error.set('Failed to load expense categories.')
    });
  }

  protected addCategory(): void {
    if (!this.newLabel) {
      return;
    }

    this.createError.set(null);

    this.expenseCategoryService.create({ label: this.newLabel, color: this.newColor }).subscribe({
      next: (category) => {
        this.categories.set([...this.categories(), category]);
        this.newLabel = '';
        this.newColor = '#1a7f37';
      },
      error: () => this.createError.set('Failed to create expense category.')
    });
  }
}
