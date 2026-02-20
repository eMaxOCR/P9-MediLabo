package com.medilabo.assessment_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {
	private String id;
	private Integer patientId;
	private String note;

}
