package com.petitpapa.business;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Ufficio
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {

    @Context
    UriInfo uriInfo;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public JsonObject getRoot() {
        return entityBuilder.buildRootDocument(uriInfo);
    }

}
