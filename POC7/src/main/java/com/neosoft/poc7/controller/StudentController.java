package com.neosoft.poc7.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.poc7.exception.ResourceNotFoundException;
import com.neosoft.poc7.model.Student;
import com.neosoft.poc7.repository.StudentRepository;
import com.neosoft.poc7.service.StudentService;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class StudentController {
	@Autowired
	StudentService Service;

	//get all users
    @GetMapping("/students")
    public List<Student> getAllStudents(@Param("keyword") String keyword) {
    	return Service.getAllStudents(keyword);
        //return userRepository.findAll();	
    }

    //get user by id
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
    	Student student = Service.findById(id);
    	
    	//User user = userRepository.findById(userId)
         // .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(student);
    }
    
    //save user
    @PostMapping("/students")
    public void createStudent(@Valid @RequestBody Student student) {
    	Service.createStudent(student);
        //return userRepository.save(user);
    }

    //update user
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateUser(@PathVariable(value = "id") Long id,
         @Valid @RequestBody Student Details) throws ResourceNotFoundException {
    	
    	final Student updatedStudent = Service.updateStudent(id, Details);
		/*
		 * User user = userRepository.findById(userId) .orElseThrow(() -> new
		 * ResourceNotFoundException("User not found for this id :: " + userId));
		 * 
		 * user.setEmailId(userDetails.getEmailId());
		 * user.setLastName(userDetails.getLastName());
		 * user.setFirstName(userDetails.getFirstName());
		 * user.setContact(userDetails.getContact()); final User updatedUser =
		 * userRepository.save(user);
		 */
        return ResponseEntity.ok(updatedStudent);
    } 

    //delete user
    @DeleteMapping("/students/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id)
         throws ResourceNotFoundException {
    	Student student = Service.findById(id);
    	//User user = userRepository.findById(userId)
      // .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

    	Service.delete(student);
        //userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
