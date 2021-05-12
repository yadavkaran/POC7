import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Student} from '../student';
import {StudentService} from '../student.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-create-student',
  templateUrl: './create-student.component.html',
  styleUrls: ['./create-student.component.css']
})
export class CreateStudentComponent implements OnInit {

  student:Student = new Student();
  submitted = false;

  constructor(private studentService: StudentService,
    private router: Router) { }

  ngOnInit(): void {
  }

  newStudent(): void{
    this.submitted = false;
    this.student = new Student();
  }

  save(){
    this.studentService.createStudent(this.student)
    .subscribe(data=>{
      console.log(data)
      this.student = new Student();
      this.gotoList();
    },
    error => console.log(error));
  }
  onSubmit() {
    this.submitted = true;
    this.save();    
  }

  gotoList() {
    this.router.navigate(['/students']);
  }

  private readonly emailRegex="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
  private readonly contactRegex="\\d{10}";

  createStudent = new FormGroup({
    firstName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    lastName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    emailId: new FormControl('',[Validators.required, Validators.pattern(this.emailRegex)])

  });

  isInvalidAndDirty(field: string): boolean{
    const ctrl = this.createStudent.get(field);
    return ctrl !==null && !ctrl.valid && ctrl.dirty;
  }

  hasError(field: string, error: string): boolean{
    const ctrl = this.createStudent.get(field);
    return ctrl !==null && ctrl.dirty && ctrl.hasError(error);
  }






}
