package com.github.adminfaces.starter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.adminfaces.starter.infra.model.*;
import com.github.adminfaces.starter.model.User;

@Repository
public interface UserRepository2 extends JpaRepository<User, Long> {
	User findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
    User findByEmail(String emailId);
	public User getUserByUsernameAndPassword(String username, String password); 

}
