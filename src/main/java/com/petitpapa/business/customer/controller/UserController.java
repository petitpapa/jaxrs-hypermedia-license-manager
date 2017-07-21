/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.business.customer.controller;

import com.petitpapa.business.customer.boundary.CustomerDao;
import com.petitpapa.business.customer.entity.Client;
import com.petitpapa.business.customer.entity.ValidLoggin;
import com.petitpapa.utilities.LoggedIn;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ufficio
 */
//@Path("login")
//@Consumes(MediaType.APPLICATION_JSON)
public class UserController implements Serializable {

    private Client client;
    @Context
    ConstraintValidatorContext constraintValidatorContext;

    @Inject
    private CustomerDao customerDao;

    public UserController() {

    }

    @POST
    public Response login(String username, String password) {

        this.client = customerDao.findCustomer(username, password);
        if (this.client != null) {
            getAuthenticatedUser();
            Logger.getLogger("").log(Level.INFO, "login success!");

            return Response.status(Response.Status.OK).build();
        } else
            return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Produces
    @LoggedIn
    public Client getAuthenticatedUser() {
        return client;
    }
}
