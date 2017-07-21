package com.petitpapa.business.customer.boundary;

import com.google.gson.Gson;
import com.petitpapa.business.EntityBuilder;
import com.petitpapa.business.customer.entity.Client;
import com.petitpapa.business.customer.entity.ValidCustomer;
import com.petitpapa.business.licenza.boundary.LicenceResource;
import com.petitpapa.exceptions.validID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alseny Cissé
 */
@RequestScoped
@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomersResource {

    @Context
    UriInfo uriInfo;

    @Inject
    EntityBuilder builder;

    @Inject
    CustomerService service;

    @Inject
    CustomerDao dao;

    @Context
    ResourceContext resourceContext;

    @GET
    public Response getCustomers() {
        JsonArray root = dao.findAll().stream()
                .map(customer -> builder.forCustomers(customer, uriInfo))
                .collect(() -> Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
        return Response.status(Response.Status.OK).entity(root).build();
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") String id) {
        //  String id = value.getString("id", null);
        final Client customer = dao.find(Long.parseLong(id));
        if (customer == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(builder.forCustomers(customer, uriInfo).build()).build();
    }

    @POST
    @Transactional
    public Response createCustomer(@ValidCustomer JsonObject jsonValue) {
        Client client = new Gson().fromJson(jsonValue.toString(), Client.class);
        dao.joinTransaction();
        if (dao.doesCustomerExists(client.getUserName()))
            return Response.status(Response.Status.FOUND).entity(Json.createObjectBuilder().add("CUSTOMER_FOUND", "The customer already exists!").build()).build();
        else {
            dao.create(client);
            Logger.getLogger(LicenceResource.class.getName()).log(Level.INFO, "The customer with name {0} was created", client.getUserName());
            return Response.status(Response.Status.CREATED).entity(builder.buildCustomer(client, uriInfo)).build();
        }
    }

    @PUT
    @Transactional
    public Response updateCustomer(@validID JsonObject value) {
        String id = value.getString("id");
        String name = value.getString("name", null);
        String company = value.getString("company", null);
        String phone = value.getString("phone", null);
        String password = value.getString("password", null);
        boolean updated = false;
        Client founded = dao.find(Long.parseLong(id));
        if (founded != null) {
            update(name, founded, updated, company, phone, password);
            return Response.status(Response.Status.OK).entity(builder.forCustomers(founded, uriInfo).build()).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).entity(Json.createObjectBuilder().add("NOT_FOUND", "The customer with id " + id + " does not exists!").build()).build();

    }

    private void update(String name, Client founded, boolean updated, String company, String phone, String password) {
        if (name != null) {
            founded.setUserName(name);
            updated = true;
        }
        if (company != null) {
            founded.setCompany(company);
            updated = true;
        }
        if (phone != null) {
            founded.setPhone(phone);
            updated = true;
        }
        if (password != null) {
            founded.setPassword(company);
            updated = true;
        }
        if (updated) {
            dao.joinTransaction();
            dao.edit(founded);
        }
    }

    @DELETE
    public Response deleteCustomer(@validID JsonObject value) {
        String id = value.getString("id");
        Client founded = dao.find(Long.parseLong(id));
        if (founded == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder().add("NOT_FOUND", "The customer with id " + id + " does not exists!").build())
                    .build();
        else {
            dao.remove(founded);
            return Response.ok().entity(Json.createObjectBuilder().add("REMOVED", "The client was successfully removed!").build()).build();
        }
    }

}
