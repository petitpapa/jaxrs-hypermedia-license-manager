package com.petitpapa.business.products.boundary;

import com.petitpapa.business.EntityBuilder;
import com.petitpapa.business.customer.boundary.CustomerDao;
import com.petitpapa.business.customer.entity.Client;
import com.petitpapa.business.licenza.boundary.LicenceResource;
import com.petitpapa.business.products.entity.Product;
import com.petitpapa.exceptions.ValidProduct;
import com.petitpapa.exceptions.validID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Alseny Cissé
 */
@Stateless
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsResource {

    @Context
    UriInfo uriInfo;

    @Inject
    ProductDao productDao;
    @Inject
    CustomerDao service;

    @Inject
    EntityBuilder builder;

    @POST
    @Transactional
    public Response createProduct(@ValidProduct JsonObject jsonObject) {
        Product p = new Product();
        productDao.joinTransaction();
        final Client customer = service.find(Long.parseLong(jsonObject.getString("clientId")));
        if (customer != null) {
            p.setClient(customer);
            p.setName(jsonObject.getString("productName"));
            p.setDescription(jsonObject.getString("description", null));

            if (productDao.doesProductExists(p.getName()))
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(Json.createObjectBuilder()
                                .add("PRODUCT_FOUNDED", "The product already exists!").build())
                        .build();
            else {

                productDao.create(p);
                Logger.getLogger(LicenceResource.class.getName()).log(Level.INFO, "The product with name {0} was created", p.getName());
                return Response.status(Response.Status.CREATED).entity(builder.buildProduct(p, uriInfo)).build();
            }

        } else
            return Response.status(Response.Status.NOT_FOUND).
                    entity(Json.createObjectBuilder()
                            .add("CUSTOMER_NOT_FOUND", "The client doesn't exists!").build())
                    .build();
    }

    @GET
    public Response allProducts() {
        return Response.status(Response.Status.OK).entity(productDao.findAll().stream()
                .map(p -> builder.forProducts(p, uriInfo))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build()).build();
    }

    @GET
    @Path("{id}")
    public Response getProduct(@validID JsonObject value) {
        Product p = find(value);

        if (p == null)
            throw new NotFoundException();
        return Response.ok().entity(builder.forProducts(p, uriInfo).build()).build();
    }

    private Product find(JsonObject value) throws NumberFormatException {
        String id = value.getString("id", null);
        final Product p = productDao.find(Long.parseLong(id));
        return p;
    }

    @DELETE
    public Response deleteProduct(@validID JsonObject value) {
        Product founded = find(value);
        if (founded == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder().add("NOT_FOUND", "The product with id " + value.getString("id") + " does not exists!").build())
                    .build();
        else {
            productDao.remove(founded);
            return Response.ok().entity(Json.createObjectBuilder().add("REMOVED", "The product was successfully removed!").build()).build();
        }
    }

    @PUT
    @Transactional
    public Response updateProduct(@validID JsonObject value) {
        String id = value.getString("id");
        String name = value.getString("productName", null);
        String description = value.getString("description", null);
        boolean updated = false;
        Product founded = productDao.find(Long.parseLong(id));
        if (founded != null) {
            update(name, founded, updated, description);
            return Response.status(Response.Status.OK).entity(builder.forProducts(founded, uriInfo).build()).build();
        } else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder().add("NOT_FOUND", "The product with id " + id + " does not exists!").build())
                    .build();
    }

    private void update(String name, Product founded, boolean updated, String description) {
        if (name != null) {
            founded.setName(name);
            updated = true;
        }
        if (description != null) {
            founded.setDescription(description);
            updated = true;
        }
        if (updated)
            productDao.edit(founded);
    }

}
