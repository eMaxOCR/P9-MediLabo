package com.medilabo.note_service.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	/**
     * Creates a new exception with a specific error message.
     *
     * @param message the message that explains why the resource was not found.
     */
	public ResourceNotFoundException(String message) {
        super(message);
    }
}
