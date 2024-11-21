import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent implements OnInit {
  studentForm: FormGroup;
  isEditMode = false;
  studentId?: number;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.studentForm = this.fb.group({
      prenomEtudiant: ['', [Validators.required]],
      nomEtudiant: ['', [Validators.required]],
      cinEtudiant: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      dateNaissance: ['', [Validators.required]]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.studentId = +id;
      this.loadStudent(this.studentId);
    }
  }

  loadStudent(id: number) {
    this.studentService.getStudentById(id).subscribe({
      next: (student) => {
        this.studentForm.patchValue({
          ...student,
          dateNaissance: new Date(student.dateNaissance).toISOString().split('T')[0]
        });
      },
      error: (err) => {
        this.error = 'Failed to load student details';
      }
    });
  }

  onSubmit() {
    if (this.studentForm.valid) {
      const studentData = this.studentForm.value;
      
      const operation = this.isEditMode
        ? this.studentService.updateStudent({ ...studentData, idEtudiant: this.studentId })
        : this.studentService.addStudent(studentData);

      operation.subscribe({
        next: () => {
          this.router.navigate(['/students']);
        },
        error: (err) => {
          this.error = 'Failed to save student';
        }
      });
    }
  }
}