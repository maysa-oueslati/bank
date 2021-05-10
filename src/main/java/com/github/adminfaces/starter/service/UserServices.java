package com.github.adminfaces.starter.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.infra.model.*;
import com.github.adminfaces.starter.model.ERole;
import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.User;
import com.github.adminfaces.starter.repository.*;



@Service
@Transactional
public class UserServices {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private UserRepository repo;

	public List<User> listAll() {
		return repo.findAll(Sort.by("email").ascending());
	}

	public User retrieveClient(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public User authenticate (String login, String password)
	{return repo.getUserByUsernameAndPassword(login, password);
	}
	
	public void addUser(User p) {
    	System.out.println("********");

		p.setIs_enabled(true);
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		p.setRoles(roles);
		repo.save(p);
	
	}
	
	public void deleteUser(Long id) {
	repo.deleteById(id);
	}
	
}
