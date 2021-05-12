package com.neosoft.poc7.test.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.poc7.controller.StudentController;
import com.neosoft.poc7.model.Student;
import com.neosoft.poc7.repository.StudentRepository;
import com.neosoft.poc7.service.StudentService;

@WebMvcTest(StudentController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentRepository repo;

	@MockBean
	private StudentService service;

	ObjectMapper objMap = new ObjectMapper();

	public static List<Student> setUp() throws ParseException {
		Student user = new Student();
		// user.setId(1);
		user.setFirstName("Dipti");
		user.setLastName("Pangam");
		user.setEmailId("pangam@gmail.com");
		

		Student user1 = new Student();
		// user1.setId(2);
		user1.setFirstName("Ankit");
		user1.setLastName("Karde");
		user1.setEmailId("karde@gmail.com");
	

		Student user2 = new Student();
		// user2.setId(3);
		user2.setFirstName("Arpita");
		user2.setLastName("Poojari");
		user2.setEmailId("poojari@gmail.com");

		List<Student> listUser = new ArrayList<>();
		listUser.add(user);
		listUser.add(user1);
		listUser.add(user2);

		return listUser;
	}

	@Test
	@WithMockUser
	public void saveUserTest() throws Exception {

		Mockito.when(service.createStudent(UserControllerTest.setUp().get(0))).thenReturn(1);

		String payload = objMap.writeValueAsString(UserControllerTest.setUp().get(0));
		MvcResult result = mockMvc
				.perform(post("/api/v1/students").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);
	}


	@Test
	@WithMockUser
	public void getStudentByIdTest() throws Exception {
		String response = objMap.writeValueAsString(UserControllerTest.setUp().get(0));
		Mockito.when(service.findById((long) 1)).thenReturn(UserControllerTest.setUp().get(0));
		MvcResult result = mockMvc.perform(get("/api/v1/students/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	@WithMockUser
	public void updateStudentTest() throws Exception {
		Mockito.when(service.updateStudent((long) 2, UserControllerTest.setUp().get(2)))
				.thenReturn(UserControllerTest.setUp().get(2));

		String payload = objMap.writeValueAsString(UserControllerTest.setUp().get(2));
		MvcResult result = mockMvc
				.perform(put("/api/v1/students/2").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		int response = result.getResponse().getStatus();
		assertEquals(200, response);
	}

	@Test
	@WithMockUser
	public void deleteStudentTest() throws Exception {
		MvcResult result = mockMvc.perform(delete("/api/v1/students/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		int response = result.getResponse().getStatus();
		assertEquals(200, response);
	}

}

