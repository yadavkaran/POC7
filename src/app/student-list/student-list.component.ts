import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {StudentDetailsComponent} from "../student-details/student-details.component";
import {StudentService} from "../student.service";
import { Student } from "../student";
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  students: Student[];

  constructor(private studentService: StudentService,private router: Router) {}

  ngOnInit(){
    this.getStudents();
  }

  private getStudents(){
    this.studentService.getStudentsList().subscribe(
      data => {
        this.students=data;
      });
      console.log(this.students);
  }

 /*  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.students = this.studentService.getStudentsList();
  } */

  deleteStudent(id:number){
    this.studentService.deleteStudent(id)
    .subscribe(
      data => {
        console.log(data);
        this.getStudents();
      },
      error => console.log(error));
  }

  studentDetails(id:number){
    this.router.navigate(['details',id]);
  }

  updateStudent(id:number){
    this.router.navigate(['update',id]);
  }

}
