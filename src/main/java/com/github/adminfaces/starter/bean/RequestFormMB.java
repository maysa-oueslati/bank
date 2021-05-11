
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.model.Request;
import com.github.adminfaces.starter.service.CarService;
import com.github.adminfaces.starter.service.IRequestService;
import com.github.adminfaces.starter.service.RequestServiceImpl;
import com.github.adminfaces.starter.util.Utils;
import com.github.adminfaces.template.exception.AccessDeniedException;
import org.omnifaces.util.Faces;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
 * @author rmpestano
 */
@Named
@ViewScoped
public class RequestFormMB implements Serializable {

    @Inject
    IRequestService requestService;

    private Long id;
    private Request req;


    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return req;
    }

    public void setRequest(Request req) {
        this.req = req;
    }


    public void remove() throws IOException {
        if (!Utils.isUserInRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("User not authorized! Only role <b>admin</b> can remove Request.");
        }
        if (has(req) && has(req.getClient())) {
        	requestService.deleteRequest(req);
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("request/request-list.jsf");
        }
    }

    public void save() {
        String msg;
        if (req.getClient() == null) {
            requestService.addRequest(req);
            msg = "Request" + req.getEmail()+ " created successfully";
        } else {
            requestService.updateRequest(req);
            msg = "Request " + req.getEmail() + " updated successfully";
        }
        addDetailMessage(msg);
    }

    public void clear() {
        req = new Request();
        id = null;
    }

    public boolean isNew() {
        return req == null || req.getClient() == null;
    }


}
