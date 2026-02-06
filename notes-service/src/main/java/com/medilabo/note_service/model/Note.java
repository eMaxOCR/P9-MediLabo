package com.medilabo.note_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "notes")
public class Note {
	
	@Id
	private String id;
	private Integer patientId;
	private String note;

}
