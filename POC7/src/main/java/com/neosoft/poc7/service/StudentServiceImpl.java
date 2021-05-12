package com.neosoft.poc7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neosoft.poc7.exception.ResourceNotFoundException;
import com.neosoft.poc7.model.Student;
import com.neosoft.poc7.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService
{

	@Autowired
	StudentRepository Repository;
	
	@Override
	public Object createStudent(Student student) {
		return Repository.save(student);
	}

	

	@Override
	public Student updateStudent(Long id, Student Details) {
		Student student = Repository.findById(id).orElseThrow(() 
				-> new ResourceNotFoundException("Not found"));
			    	student.setEmailId(Details.getEmailId());
			    	student.setLastName(Details.getLastName());  
			        student.setFirstName(Details.getFirstName());
			        final Student updatedUser = Repository.save(student);
			        return updatedUser;
	}

	@Override
	public Student findById(Long id) {
		Student student = Repository.findById(id)
		         .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
		      return student;
	}

	@Override
	public void delete(Student student) {
		Repository.delete(student);
		
	}



	@Override
	public List<Student> getAllStudents(String keyword) {
		if (keyword == null) {
			return this.Repository.findAll();
			
		}
		return this.Repository.search(keyword);
	}

}
