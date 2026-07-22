import { Component, OnInit, inject, signal } from '@angular/core';
import { ClientSideRowModelModule, ColDef, ModuleRegistry, themeQuartz } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { HealthService } from './health.service';
import { HouseholdMember } from './household-member';
import { HouseholdMemberService } from './household-member.service';

ModuleRegistry.registerModules([ClientSideRowModelModule]);

type HealthStatus = 'loading' | 'up' | 'error';

@Component({
  selector: 'app-root',
  imports: [AgGridAngular],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private readonly healthService = inject(HealthService);
  private readonly householdMemberService = inject(HouseholdMemberService);

  protected readonly healthStatus = signal<HealthStatus>('loading');

  protected readonly householdMembers = signal<HouseholdMember[]>([]);
  protected readonly householdMembersError = signal<string | null>(null);
  protected readonly gridTheme = themeQuartz;

  protected readonly columnDefs: ColDef<HouseholdMember>[] = [
    { field: 'firstName', headerName: 'First name' },
    { field: 'lastName', headerName: 'Last name' },
    { field: 'dateOfBirth', headerName: 'Date of birth' },
    { field: 'countryOfResidence', headerName: 'Country of residence' },
    { field: 'countryOfEmployment', headerName: 'Country of employment' },
    { field: 'averageMonthlySalary', headerName: 'Avg. monthly salary' },
    { field: 'currentCash', headerName: 'Current cash' }
  ];

  ngOnInit(): void {
    this.healthService.check().subscribe({
      next: () => this.healthStatus.set('up'),
      error: () => this.healthStatus.set('error')
    });

    this.householdMemberService.findAll().subscribe({
      next: (members) => this.householdMembers.set(members),
      error: () => this.householdMembersError.set('Failed to load household members.')
    });
  }
}
