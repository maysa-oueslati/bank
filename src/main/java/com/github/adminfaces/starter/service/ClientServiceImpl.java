package com.github.adminfaces.starter.service;
import com.github.adminfaces.starter.model.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.bean.ClientListMB;
import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.repository.*;



@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	ClientRepository ClientRepository;

	@Autowired
	UserServices ur;

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ScoreRepository SR;
	@Autowired
	ClientService cs;

	@Override
	public List<Client> retrieveAllClients() {
		return (List<Client>) ClientRepository.findAll();
	}

	@Override
	public String addClient(Client p) {
		p.setIs_enabled(false);
		p.setScore(0.0);
		p.setGroupe("New");
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		p.setRoles(roles);
		ClientRepository.save(p);
		cs.init();
		return "please check your mail to complete registration";
  
	}

	@Override
	public void deleteClient(Long id) {
		ClientRepository.deleteById((long) id);
	}

	@Override
	public Client updateClient(Client p) {
		return ClientRepository.save(p);
	}

	@Override
	public Client retrieveClient(Long id) {
		return ClientRepository.findById(id).orElse(null);
	}

	@Override
	public List<Client> retrieveClientByScore(Double score) {
		return ClientRepository.SearchClientByScore(score);
	}

	@Override
	public List<Client> SearchClientByName(String cin) {

		return ClientRepository.SearchClientByName(cin);

	}

	@Override
	public Double CalculScore(long id) {

		return 1.2;

	}

	@Override
	public void CalculScoreAndAffectToUclients() {

		for (Client c : retrieveAllClients()) {
			c.setScore(CalculScore(c.getId()));
			ClientRepository.save(c);
		}
	}
}
