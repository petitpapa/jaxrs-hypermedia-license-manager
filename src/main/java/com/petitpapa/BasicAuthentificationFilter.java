/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa;

import com.petitpapa.business.customer.controller.UserController;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alseni
 */
public class BasicAuthentificationFilter implements ContainerRequestFilter {

    @Inject
    private UserController userController;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> userCredentials = null;
        String authorizationHeader = requestContext.getHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic")) {
            //Authorization: Basic YWxhZGRpbjpvcGVuc2VzYW1l
            //authorizationHeader.substring("Basic".length()).trim()
            //  Arrays.toString(Base64.getDecoder().decode(authorizationHeader.substring("Basic".length()).trim().getBytes()));
            userCredentials = Pattern.compile(":")
                    .splitAsStream(Arrays.toString(Base64.getDecoder().decode(authorizationHeader.substring("Basic".length()).trim().getBytes())))
                    .collect(Collectors.toList());
            userController.login(userCredentials.get(0), userCredentials.get(1));
        }
        Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource").build();
        requestContext.abortWith(unauthorizedStatus);
    }

}
