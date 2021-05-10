/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.model.ERole;
import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.User;
import com.github.adminfaces.starter.service.ClientService;
import com.github.adminfaces.starter.service.UserService;
import com.github.adminfaces.starter.util.Utils;
import com.github.adminfaces.template.exception.AccessDeniedException;
import org.omnifaces.util.Faces;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
 * @author rmpestano
 */
@Named
@ViewScoped
public class UserFormMB implements Serializable {

    @Inject
    UserService carService;

    private Long id;
    private User car;


    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            car = carService.findById(id);
        } else {
            car = new User();
        }
    }
	public ERole[] getRoles() { return ERole.values(); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCar() {
        return car;
    }

    public void setCar(User car) {
        this.car = car;
    }


    public void remove() throws IOException {
        if (!Utils.isUserInRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("User not authorized! Only role <b>admin</b> can remove Clients.");
        }
        if (has(car) && has(car.getId())) {
            carService.remove(car);
            addDetailMessage("Client " + car.getUsername()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("user/client-list.jsf");
        }
    }

    public void save() {
    	System.out.println("********");
        String msg;
        if (car.getId() == null) {
            carService.insert(car);
            msg = "User " + car.getUsername() + " created successfully";
        } else {
            carService.update(car);
            msg = "User " + car.getUsername() + " updated successfully";
        }
        addDetailMessage(msg);
    }

    public void clear() {
        car = new User();
        id = null;
    }

    public boolean isNew() {
        return car == null || car.getId() == null;
    }


}
