package com.medilabo.note_service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medilabo.note_service.configuration.ResourceNotFoundException;
import com.medilabo.note_service.model.Note;
import com.medilabo.note_service.repository.NoteRepository;

@Service
public class NoteService {
	
	@Autowired
	private NoteRepository noteRespository;
	
	/**
	 * Get one note by it's ID 
	 * @return Note
	 * */
	public Note findById(String id)	{
		return noteRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note not found with id : " + id));

	}
	
	/**
	 * Get list of patient's note
	 * */
	public Iterable<Note> getAllPatientNote(Integer id){
		return noteRespository.findByPatientId(id);
	}
	
	/**
	 * Create note
	 * @param new Note
	 * @return Note
	 * */
	public Note save(Note note) {
		return noteRespository.save(note);
	}
	
	/**
	 * Update note informations
	 * @param note with new informations
	 * @return note
	 * */
	public Note update(String id, Note newInformations) {
		
		Note optionalPatient = findById(id);
		
		if (optionalPatient!=null) {
			Note existingNote = optionalPatient;
	        BeanUtils.copyProperties(newInformations, existingNote, "id");
	        return save(existingNote);
	    } else{
	    	throw new ResourceNotFoundException("Note not found with id: " + id);
	    }
	}
	
	/**
	 * Delete one note 
	 * @param Note's ID
	 * */
	public void delete(String id) {
		if(findById(id).getId()==null) {
			throw new ResourceNotFoundException("Note not found with that id : " + id);
		}
		noteRespository.deleteById(id);
	}

}
