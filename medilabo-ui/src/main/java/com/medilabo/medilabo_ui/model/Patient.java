package com.medilabo.medilabo_ui.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
  
@Entity
@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "lastname", "birthdate", "genre", "address", "phoneNumber" })
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String lastname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birthdate;
	@Enumerated(EnumType.STRING)
	private Gender genre;
	private String address;
	@Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Format invalide")
	private String phoneNumber;

}

