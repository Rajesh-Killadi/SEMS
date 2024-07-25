package com.app.controllers;

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
public class CounsellorController {

	private CounsellorService service;

	@Autowired
	public void setService(CounsellorService service) {
		this.service = service;
	}

	@GetMapping("/")
	public String indexPage(Model model) {

		model.addAttribute("counsellor", new Counsellors());

		return "index.html";

	}

	@GetMapping(value = "/register")
	public String registrationPage(Model model) {

		model.addAttribute("counsellor", new Counsellors());

		return "registration";
	}

	@GetMapping(value = "/forgotPassword")
	public String forgotPasswordPage(Model model) {

		model.addAttribute("counsellor", new Counsellors());

		return "forgotPassword.html";

	}

	@PostMapping(value = "/registerCounsellor")
	public String regitsterCounsellor(Counsellors counselllor, Model model) {

		boolean counsellorRegistration = service.counsellorRegistration(counselllor);

		if (counsellorRegistration) {

			model.addAttribute("registrationSuccess", "Registration Success");
		} else {
			model.addAttribute("registrationFailed", "Email is Already Registred");
		}
		model.addAttribute("counsellor", new Counsellors());

		return "registration.html";

	}

	@PostMapping(value = "/login")
	public String login(Model model, Counsellors counsellor) {

		CounsellorModel counsellorModel = service.counsellorLogin(counsellor);

		if (counsellorModel == null) {

			model.addAttribute("loginMessage", "Invalid Credentials");
			model.addAttribute("counsellor", new Counsellors());

			return "index.html";
		}

		Integer[] findCountOfEnquires = service.findCountOfEnquires(counsellorModel.getId());

		model.addAttribute("totalEnquiries", findCountOfEnquires[0]);
		model.addAttribute("enrolled", findCountOfEnquires[1]);
		model.addAttribute("lost", findCountOfEnquires[2]);
		model.addAttribute("counsellorModel", counsellorModel);

		return "dashboard.html";

	}

	@PostMapping(value = "/generatePassword")
	public String forgotPassword(Counsellors counsellor, Model model) {

		boolean forgotPassword = service.forgotPassword(counsellor.getEmail());

		if (forgotPassword) {
			model.addAttribute("resultSuccess", "Password sent to your registred email");
		} else {
			model.addAttribute("resultFail", "Email is not Registred");
		}
		model.addAttribute("counsellor", new Counsellors());

		return "forgotPassword.html";
	}

}
