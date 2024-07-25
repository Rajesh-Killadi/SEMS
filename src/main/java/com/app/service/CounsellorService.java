package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.entities.Counsellors;
import com.app.entities.Students;
import com.app.model.CounsellorModel;

@Service
public interface CounsellorService {

  boolean counsellorRegistration(Counsellors counsellor);
  
  CounsellorModel counsellorLogin(Counsellors counsellor);
  
  boolean forgotPassword(String email);
  
  boolean addStudentEnquiry(Students students);
  
  
  List<Students>getAllEnquiriesOfCounsellor(Students student);
  
  Integer[] findCountOfEnquires(Integer counsellorId);
  
}


