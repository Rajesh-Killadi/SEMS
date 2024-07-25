package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Counsellors;
import com.app.entities.Students;

public interface StudentsDao extends JpaRepository<Students, Integer> {

	@Query("select count(e) from Students e where e.enquiryStatus = :enquiryStatus and e.counsellorId = :counsellorId")
	Integer countByEnquiryStatus(String enquiryStatus ,Counsellors counsellorId );
	
	@Query("select count(e) from Students e where e.counsellorId =:counsellorId ")
	Integer countByEnquiryStatus(Counsellors counsellorId );
	
	
	
}
