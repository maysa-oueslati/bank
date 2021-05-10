package com.github.adminfaces.starter.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.model.User;
import com.github.adminfaces.starter.repository.UserRepository;
import com.github.adminfaces.starter.repository.UserRepository2;

@Named
@ViewScoped
public class SessionMB implements Serializable {

	private String currentUser;
	private Long idcurrent;
	@Autowired
	UserRepository2 rep;

	@PostConstruct
	public void init() {
		currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

	}

	public Long getIdcurrent() {
		idcurrent=rep.findByUsername(currentUser).getId();
		return idcurrent;
	}

	public void setIdcurrent(Long idcurrent) {
		this.idcurrent = idcurrent;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}
