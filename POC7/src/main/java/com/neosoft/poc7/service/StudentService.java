package com.neosoft.poc7.service;

import java.util.List;



import com.neosoft.poc7.model.Student;



public interface StudentService {
	
	public Object createStudent(Student student);

	public List<Student> getAllStudents(String keyword);

	public Student updateStudent(Long id, Student Details);

	public Student findById(Long id);

	public void delete(Student student);




}

