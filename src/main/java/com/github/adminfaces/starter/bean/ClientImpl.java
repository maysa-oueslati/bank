package com.github.adminfaces.starter.bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;



import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;


import com.github.adminfaces.starter.infra.model.*;
import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.model.ERole;
import com.github.adminfaces.starter.model.User;
import com.github.adminfaces.starter.service.ClientServiceImpl;
import com.github.adminfaces.starter.service.UserDetailsImpl;
import com.github.adminfaces.starter.service.UserServices;

@Scope(value = "session")
@Controller(value = "ClientController") // Name of the bean in Spring IoC
@ELBeanName(value = "ClientController") // Name of the bean used by JSF
@Join(path = "/", to = "/signUp.jsf")

public class ClientImpl {

	@Autowired
	ClientServiceImpl cs;
	@Autowired
	UserServices us;
	private long id;
	private List<Client> clients;
	private List<User> users;

	private String phnb;
	private String username;
	private String password;
	private String email;

	private String cin;

	private String adress;

	private String first_name;

	private String last_name;
	
	private Date date_of_birth;

	private LocalDate join_date = LocalDate.now();;
	private Double score;

	private String groupe;

	public ERole[] getRoles() { return ERole.values(); }

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Client> getClients() {
		clients = cs.retrieveAllClients();
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public List<User> getUsers() {
		users=us.listAll();
		return users;
	}



	public void setUsers(List<User> users) {
		this.users = users;
	}



	public String getPhnb() {
		return phnb;
	}

	public void setPhnb(String phnb) {
		this.phnb = phnb;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public LocalDate getJoin_date() {
		return join_date;
	}

	public void setJoin_date(LocalDate join_date) {
		this.join_date = join_date;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public void addclient() {
		System.out.println("*****");
		Client c = new Client(username, email, password, cin, adress, first_name, last_name, date_of_birth, phnb);
		cs.addClient(c);
	}


	public void removeClient(long userId) {
		cs.deleteClient(userId);

	}
	public void removeUserr(long userId) {
		System.out.println("**********");		System.out.println("**********");
		System.out.println("**********");
		System.out.println("**********");

		us.deleteUser(userId);

	}

	public void update() {
		Client c = new Client(userIdToBeUpdated, username, email, password, cin, adress, first_name, last_name,
				date_of_birth, phnb);
		cs.updateClient(c);

	}

	private long userIdToBeUpdated;

	public long getUserIdToBeUpdated() {
		return userIdToBeUpdated;
	}

	public void setUserIdToBeUpdated(long userIdToBeUpdated) {
		this.userIdToBeUpdated = userIdToBeUpdated;
	}

	public void displayUser(Client c) {
		this.setEmail(c.getEmail());
		this.setPassword(c.getPassword());
		this.setUsername(c.getUsername());
		this.setAdress(c.getAdress());
		this.setEmail(c.getEmail());
		this.setDate_of_birth(c.getDate_of_birth());
		this.setCin(c.getCin());
		this.setFirst_name(c.getFirst_name());
		this.setLast_name(c.getLast_name());
		this.setUserIdToBeUpdated(c.getId());
	}




	public String doLogin() {
		
			return""; 
	}
	@Autowired
	UserServices userServices;
	public void addUser() {
		System.out.println(username);
		System.out.println(email);
		System.out.println(password);
		User c = new User(username, email, password);
		userServices.addUser(c);
		
	}

}
