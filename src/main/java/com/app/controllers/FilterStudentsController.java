package com.app.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Counsellors;
import com.app.entities.Students;
import com.app.model.StudentsSearchModel;
import com.app.service.ServiceImplementation;

@RestController
public class FilterStudentsController {

	@Autowired
	ServiceImplementation service;
	
	@PostMapping("/filterStudents")
	public List<Students> filterStudents(@RequestBody StudentsSearchModel searchModel){
		
		Students students = new Students();
		
		if (!searchModel.getClassMode().isEmpty()) {
			students.setClassMode(searchModel.getClassMode());
		}
		if (!searchModel.getCourse().isEmpty()) {
			
			students.setCourse(searchModel.getCourse());
		}
		if (!searchModel.getEnquiryStatus().isEmpty()) {
			
			 students.setEnquiryStatus(searchModel.getEnquiryStatus());
		}
		
		
		
		Counsellors counsellor = new Counsellors();
		counsellor.setId(searchModel.getCounsellorId());
		
		students.setCounsellorId(counsellor);
		
		List<Students> studentsList = service.getAllEnquiriesOfCounsellor(students);
		
		return studentsList;
		
	}

}
