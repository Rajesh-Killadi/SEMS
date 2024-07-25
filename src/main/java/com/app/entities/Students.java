package com.app.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Students {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;
	private String studentName;
	private String contactNo;
	private String classMode;
	private String course;
	private String enquiryStatus;
	@CreationTimestamp
    @Temporal(TemporalType.DATE) // Use TemporalType.DATE to store only the date without time
    @Column(name = "enquiry_date", updatable = false)
    private LocalDate enquiryDate;


	@ManyToOne
	@JoinColumn(name = "counsellorId",referencedColumnName = "id")
	private Counsellors counsellorId;

}
