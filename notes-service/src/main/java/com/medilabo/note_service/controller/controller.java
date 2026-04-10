package com.medilabo.note_service.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.medilabo.note_service.model.Note;
import com.medilabo.note_service.service.NoteService;

@RequestMapping("/note")
@RestController
public class controller {
	@Autowired
	private NoteService noteService;

	/**
	 * Get note information
	 * 
	 * @return note
	 */
	@GetMapping("/{id}")
	public Note getNote(@PathVariable("id") String id) {
		return noteService.findById(id);

	}

	/**
	 * Get list of patient's notes
	 * 
	 * @return list of notes.
	 */
	@GetMapping("/user/{id}")
	public Iterable<Note> getAllPatientNote(@PathVariable("id") Integer id) {
		return noteService.getAllPatientNote(id);
	}

	/**
	 * Create note
	 * 
	 * @param note
	 */
	@PostMapping()
	public ResponseEntity<Note> save(@RequestBody Note note) {

		Note newNote = noteService.save(note);
		URI existingNote = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newNote.getPatientId()).toUri(); // Sent URI to header.
		return ResponseEntity.created(existingNote).body(newNote);
	}

	/**
	 * Delete one patient's note
	 * 
	 * @param note's id
	 */
	@DeleteMapping("/{id}")
	public void deletePatient(@PathVariable("id") String id) {
		noteService.delete(id);
	}
}
