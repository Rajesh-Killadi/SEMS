package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.entities.Counsellors;
import com.app.entities.Students;
import com.app.model.CounsellorModel;
import com.app.repositories.CounsellorsDao;
import com.app.repositories.StudentsDao;

@Service
public class ServiceImplementation implements CounsellorService {

	private CounsellorsDao counsellorDao;
	private StudentsDao studentsDao;
	private JavaMailSender mailSender;

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Autowired
	public void setCounsellorDao(CounsellorsDao counsellorDao) {
		this.counsellorDao = counsellorDao;
	}

	@Autowired
	public void setStudentsDao(StudentsDao studentsDao) {
		this.studentsDao = studentsDao;
	}

	@Override
	public boolean counsellorRegistration(Counsellors counsellor) {

		Counsellors findByEmail = counsellorDao.findByEmail(counsellor.getEmail());

		if (findByEmail == null) {

			counsellorDao.save(counsellor);

			return true;
		}

		return false;
	}

	@Override
	public CounsellorModel counsellorLogin(Counsellors counsellor) {

		Example<Counsellors> ex = Example.of(counsellor);

		Optional<Counsellors> findOne = counsellorDao.findOne(ex);

		if (findOne.isPresent()) {

		  CounsellorModel counsellorModel = new CounsellorModel();
			BeanUtils.copyProperties(findOne.get(), counsellorModel);
			return counsellorModel ;

		}

		return null;
	}

	@Override
	public boolean forgotPassword(String email) {
		
		Counsellors findByEmail = counsellorDao.findByEmail(email);

		if (findByEmail != null) {
			
			String msg = "Your password is :"+findByEmail.getPassword();
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(email);
			mail.setSubject("Password");
			mail.setText(msg);
			mailSender.send(mail);
			
			
			return true;
			
		}

		return false;
	}
		

	@Override
	public boolean addStudentEnquiry(Students students) {

		studentsDao.save(students);
		return true;
	}

	@Override
	public List<Students> getAllEnquiriesOfCounsellor(Students students) {
		
		Example<Students> of = Example.of(students);
		List<Students> list = studentsDao.findAll(of);
		
		return list;

	}
	
	
	@Override
	public Integer[] findCountOfEnquires(Integer counsellorId) {
		
		 Counsellors counsellor = new Counsellors();
		 counsellor.setId(counsellorId);
		
		 Integer totalEnquiries = studentsDao.countByEnquiryStatus(counsellor);
		 Integer enrolled = studentsDao.countByEnquiryStatus("enrolled",counsellor);
		 Integer lost = studentsDao.countByEnquiryStatus("lost",counsellor);
		 
		 return new Integer[] {totalEnquiries,enrolled,lost};
		 
	}


}
