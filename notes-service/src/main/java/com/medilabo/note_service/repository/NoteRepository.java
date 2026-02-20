package com.medilabo.note_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.note_service.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String>{

	public List<Note> findByPatientId(Integer patientId);

}
