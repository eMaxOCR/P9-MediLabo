package com.medilabo.assessment_service.model;

import lombok.Getter;

@Getter
public enum LevelRisk{
	EARLY_ONSET("Early on set"),
	IN_DANGER("In danger"),
	BORDERLINE("Borderline");
	
	private LevelRisk(String libelle) {
		this.libelle = libelle;
	}

	private String libelle;
	
}