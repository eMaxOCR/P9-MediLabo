package com.medilabo.assessment_service.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {

	private Integer id;
	private String name;
	private String lastname;
	private Date birthdate; 
	private Gender genre;
	private String address;
	private String phoneNumber;

}

