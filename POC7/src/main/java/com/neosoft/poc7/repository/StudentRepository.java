package com.neosoft.poc7.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neosoft.poc7.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	@Query("SELECT u FROM Student u WHERE u.firstName LIKE %?1%"
            + " OR u.lastName LIKE %?1%")
	public List<Student> search(String keyword);
}
