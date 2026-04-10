package com.medilabo.note_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection = "notes")
public class Note {
	
	@Id
	private String id;
	private Integer patientId;
	private String note;

}
