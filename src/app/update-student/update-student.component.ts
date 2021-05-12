import { Component, OnInit } from '@angular/core';
import { Router,ActivatedRoute } from '@angular/router';
import {Student} from '../student';
import {StudentService} from '../student.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-student',
  templateUrl: './update-student.component.html',
  styleUrls: ['./update-student.component.css']
})
export class UpdateStudentComponent implements OnInit {

  id: number;
  student: Student;
  submitted: boolean = false;

  private readonly emailRegex="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
  private readonly contactRegex="\\d{10}";

  updateStudentValid = new FormGroup({
    firstName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    lastName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    emailId: new FormControl('',[Validators.required, Validators.pattern(this.emailRegex)]),
    contact: new FormControl('',[Validators.required, Validators.pattern(this.contactRegex)]),

  });

  isInvalidAndDirty(field: string): boolean{
    const ctrl = this.updateStudentValid.get(field);
    return ctrl !==null && !ctrl.valid && ctrl.dirty;
  }

  hasError(field: string, error: string): boolean{
    const ctrl = this.updateStudentValid.get(field);
    return ctrl !==null && ctrl.dirty && ctrl.hasError(error);
  }

  constructor(private route: ActivatedRoute, private router: Router,
    private userService: StudentService) { }

  ngOnInit(){
    this.student = new Student();
    this.id = this.route.snapshot.params['id'];
    this.userService.getStudent(this.id).subscribe(
      data => {
        console.log(data)
        this.student = data;
      }, error => console.log(error));
  }

  updateStudent(){
    this.userService.updateStudent(this.id, this.student).subscribe(
      data => {
        console.log(data)
        this.student = new Student();
        this.gotoList();
      }, error => console.log(error));
  }

  onSubmit() {
    this.updateStudent();   
  }

  gotoList() {
    this.router.navigate(['students']);
  }

}
