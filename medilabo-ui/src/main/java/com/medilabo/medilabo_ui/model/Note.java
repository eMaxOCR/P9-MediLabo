package com.medilabo.medilabo_ui.model;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {
	
	@Id
	private String id;
	private Integer patientId;
	private String note;

}
