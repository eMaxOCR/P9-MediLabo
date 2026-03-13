package com.medilabo.medilabo_ui.model;

import com.fasterxml.jackson.annotation.JsonAlias;
//import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {
	
	//@ID
	@JsonProperty("id")
    @JsonAlias({"_id", "id"})
	private String id;
	private Integer patientId;
	private String note;

}
