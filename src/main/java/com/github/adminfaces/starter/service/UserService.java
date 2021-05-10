/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.service;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.model.SortOrder;
import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.User;
import com.github.adminfaces.starter.repository.ClientRepository;
import com.github.adminfaces.starter.repository.UserRepository;
import com.github.adminfaces.template.exception.BusinessException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.adminfaces.template.util.Assert.has;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author rmpestano Car Business logic
 */
@Component
public class UserService implements Serializable {


	List<User> allCars;
	Collection<? extends GrantedAuthority> authus;

	
	@Autowired
	UserRepository rep;
	@Autowired
	UserDetailsService userdet;
	
	
	
	
	
	   public Collection<? extends GrantedAuthority> getAuthus() {
		   for(User u :allCars){
			   UserDetails cc=userdet.loadUserByUsername(u.getUsername());
			   Collection<? extends GrantedAuthority> dd = cc.getAuthorities();
			 
				List<String> roles = cc.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());
			  System.out.print(roles);
		   }
		  
		
		return authus;
	}

	public void setAuthus(Collection<? extends GrantedAuthority> authus) {
		this.authus = authus;
	}

	@PostConstruct
	    public void init() {
	    	allCars =(List<User>) rep.findAll();	
	    }

	public List<User> getAllCars() {
		allCars = (List<User>) rep.findAll();
		return allCars;
	}

	public void setAllCars(List<User> allCars) {
		this.allCars = allCars;
	}

	public void insert(User car) {
		car.setId(allCars.stream().mapToLong(c -> c.getId()).max().getAsLong() + 1);
		allCars.add(car);
		rep.save(car);
	}

	public void remove(User car) {
		allCars.remove(car);
		rep.delete(car);
	}

	public User findById(Long id) {
		return allCars.stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new BusinessException("User not found with id " + id));
	}

	public void update(User car) {
		allCars.remove(allCars.indexOf(car));
		allCars.add(car);
		rep.save(car);
		
	}
	
	
	
	
	
}
