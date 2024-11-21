import { Routes } from '@angular/router';

export const routes: Routes = [
  { 
    path: 'students', 
    loadComponent: () => import('./components/student-list/student-list.component')
      .then(m => m.StudentListComponent) 
  },
  { 
    path: 'students/new', 
    loadComponent: () => import('./components/student-form/student-form.component')
      .then(m => m.StudentFormComponent) 
  },
  { 
    path: 'students/edit/:id', 
    loadComponent: () => import('./components/student-form/student-form.component')
      .then(m => m.StudentFormComponent) 
  },
  { path: '', redirectTo: '/students', pathMatch: 'full' }
];
