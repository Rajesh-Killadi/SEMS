package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Counsellors;

public interface CounsellorsDao extends JpaRepository<Counsellors , Integer> {
	
	Counsellors findByEmail(String email);

}
