package com.app.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.entities.Counsellors;
import com.app.entities.Students;
import com.app.model.CounsellorModel;
import com.app.model.StudentsModel;
import com.app.service.CounsellorService;

@Controller
public class EnquiryController {
	@Autowired
	CounsellorService service;

	@GetMapping(value = "/enquiryRegistration")
	public String enquiryPage(Model model, @RequestParam("counsellorId") Integer counsellorId) {

		model.addAttribute("studentsModel", new StudentsModel());
		model.addAttribute("counsellorId", counsellorId);

		return "addEnquiries";

	}
	@GetMapping("/dashboard")
	public String dashboardPage(Model model ,@RequestParam("counsellorId") Integer counsellorId) {
		
		Integer[] findCountOfEnquires = service.findCountOfEnquires(counsellorId);

		CounsellorModel counsellorModel = new CounsellorModel();
		counsellorModel.setId(counsellorId);
		
		model.addAttribute("totalEnquiries", findCountOfEnquires[0]);
		model.addAttribute("enrolled", findCountOfEnquires[1]);
		model.addAttribute("lost", findCountOfEnquires[2]);
		model.addAttribute("counsellorModel", counsellorModel);

		return "dashboard";
		
	}

	/*
	 * @GetMapping("/viewEnquiryPage") public String viewEnquiriesPage(Model
	 * model, @RequestParam("counsellorId") Integer counsellorId) {
	 * 
	 * model.addAttribute("counsellorId", counsellorId); return
	 * "viewEnquiries.html"; }
	 */

	@PostMapping("/addEnquiry")
	public String addEnquiry(StudentsModel studentsModel, Model model ,@RequestParam("counsellorId") Integer counsellorId ) {


		Students students = new Students();
		BeanUtils.copyProperties(studentsModel, students);
		

		Counsellors counsellor = new Counsellors();
		
		counsellor.setId(studentsModel.getCounsellorId());

		students.setCounsellorId(counsellor);

		boolean addStudentEnquiry = service.addStudentEnquiry(students);

		if (addStudentEnquiry) {
			model.addAttribute("statusSuccess", "Enquiry Added");
		} else {
			model.addAttribute("statusFailed", "Enquiry Failed to Add");
		}
		model.addAttribute("counsellorId", studentsModel.getCounsellorId());
		model.addAttribute("studentsModel", new StudentsModel());

		return "addEnquiries";

	}
	
	@GetMapping("/viewEnquiries")
	public String viewEnquiries(Model model ,@RequestParam("counsellorId") Integer counsellorId){
		
		Students students = new Students();
		Counsellors counsellor = new Counsellors();
		counsellor.setId(counsellorId);
		students.setCounsellorId(counsellor);
		
		List<Students> studentsList = service.getAllEnquiriesOfCounsellor(students);
		
		model.addAttribute("studentsList", studentsList);
		model.addAttribute("counsellorId", counsellorId);
		
		return "viewEnquiries";
		
		
	}
	
	
}
