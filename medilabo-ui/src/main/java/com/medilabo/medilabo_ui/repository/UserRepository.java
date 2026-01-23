package com.medilabo.medilabo_ui.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.medilabo_ui.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	/**
	 * Searching User by it's "username".
	 * @param username.
	 * */
	public Optional<User> findByUsername(String username);

	
}
