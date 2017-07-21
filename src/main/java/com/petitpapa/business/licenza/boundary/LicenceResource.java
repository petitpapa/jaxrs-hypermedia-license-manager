package com.petitpapa.business.licenza.boundary;

import com.petitpapa.business.EntityBuilder;
import com.petitpapa.business.licenza.control.LicenseBuilder;
import com.petitpapa.business.licenza.entity.Licenza;
import com.petitpapa.business.licenza.entity.Status;
import com.petitpapa.business.products.boundary.ProductDao;
import com.petitpapa.business.products.entity.Product;
import com.petitpapa.exceptions.ValidLicense;
import com.petitpapa.exceptions.validID;
import com.petitpapa.exceptions.validUpdateLicense;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
 * @author Alseny cissé
 */
@Stateless
@Path("licenze")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LicenceResource {

    @Inject
    private LicenceDao dao;
    @Inject
    private ProductDao productDao;
    @Context
    UriInfo uriInfo;
    @Inject
    EntityBuilder builder;
    @Inject
    LicenseBuilder licenseBuilder;

    @Resource
    TimerService timerService;

    @GET
    public JsonArray getLicences() {
        return dao.findAll().stream()
                .map(l -> builder.forLicense(l, uriInfo))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
    }

    @POST
    @Transactional
    public Response createLicense(@ValidLicense JsonObject value) throws UnsupportedEncodingException {
        Licenza l = new Licenza();
        l.setLimitDate(LocalDate.parse(value.getString("limitDate"), DateTimeFormatter.ofPattern("d-MM-yyyy")));
        Product product = productDao.find(Long.parseLong(value.getString("productId")));
        if (product != null) {
            l.setProduct(product);
            dao.create(l);
            licenseBuilder.generateKey(product.getClient(), l);
            dao.edit(l);

            ScheduleExpression expiredDateSchedule = new ScheduleExpression().dayOfMonth(l.getLimitDate().getDayOfMonth()).month(l.getLimitDate().getMonth().getValue());
            timerService.createCalendarTimer(expiredDateSchedule, new TimerConfig(l, true));

            Logger.getLogger(LicenceResource.class.getName()).log(Level.INFO, "The license with id {0} was created", l.getId());

            return Response.status(Response.Status.CREATED).entity(builder.forLicense(l, uriInfo).build()).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("PRODUCT_NOT_FOUND", "The product with id " + value.getString("productId") + " does not exists").build()).build();
    }

    @Timeout
    public void updateLicenseStatusWhenExpired(Timer info) {
        Licenza l = (Licenza) info.getInfo();
        l.setStatus(Status.EXPIRED);
    }

    @PUT
    @Transactional
    public Response updateLicense(@validUpdateLicense JsonObject value) {
        String licenseId = value.getString("licenseId");
        String days = value.getString("numberOfDays");
        boolean onExpiring = value.getBoolean("onExpired", false);

        Licenza founded = dao.find(Long.parseLong(licenseId));
        if (founded == null)
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("LICENSE_NOT_FOUND", "The license with id" + value.getString("licenseId") + " does not exists").build()).build();
        else {
            if (days != null)
                founded.renewLicenceDate(Integer.parseInt(days), onExpiring);
            dao.edit(founded);

            Logger.getLogger(LicenceResource.class.getName()).log(Level.INFO, "added {0} days to license with id {1}", new Object[]{days, founded.getId()});

            return Response.status(Response.Status.CREATED).entity(builder.forLicense(founded, uriInfo).build()).build();
        }

    }

    @GET
    @Path("{id}")
    @Transactional
    public Response getLicense(@validID JsonObject value) {
        String id = value.getString("id", null);
        Licenza founded = dao.find(Long.parseLong(id));
        if (founded == null)

            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("LICENSE_NOT_FOUND", "The license with id" + Long.parseLong(id) + " does not exists").build()).build();
        else {

            LocalDate duedate = founded.getLimitDate();
            if (duedate.isBefore(LocalDate.now())) {
                founded.setStatus(Status.EXPIRED);
                dao.edit(founded);

                Logger.getLogger(LicenceResource.class.getName()).log(Level.INFO, "The license with id {0} status: {1}", new Object[]{founded.getId(), founded.getStatus()});

                Response.status(Response.Status.OK).entity(Json.createObjectBuilder().add("expired", "License gia scudata").build()).build();
            }
            if (duedate.isAfter(LocalDate.now())) {

                Logger.getLogger(LicenceResource.class.getName()).log(Level.INFO, "The license with id {0} status: {1}", new Object[]{founded.getId(), founded.getStatus()});

                return Response.status(Response.Status.OK)
                        .entity(builder.forLicense(founded, uriInfo).build())
                        .build();
            }

            return Response.serverError().build();
        }
    }

    @DELETE
    public Response deleteLicense(@validID JsonObject value) {
        String id = value.getString("id");
        Licenza founded = dao.find(Long.parseLong(id));
        if (founded == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder().add("NOT_FOUND", "The license with id " + id + " does not exists!").build())
                    .build();
        else {
            dao.remove(founded);
            return Response.ok().entity(Json.createObjectBuilder().add("REMOVED", "The license was successfully removed!").build()).build();
        }
    }
}
