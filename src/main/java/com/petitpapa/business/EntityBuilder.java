package com.petitpapa.business;

import com.petitpapa.business.customer.entity.Client;
import com.petitpapa.business.licenza.entity.Licenza;
import com.petitpapa.business.products.entity.Product;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Alseny Cissé
 */
public class EntityBuilder {

    @Inject
    LinkBuilder linkBuilder;

    JsonObject buildRootDocument(UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("links",
                        Json.createArrayBuilder().add(
                                Json.createObjectBuilder().add("products", linkBuilder.forProducts(uriInfo).toString()).build())
                                .add(Json.createObjectBuilder().add("customers", linkBuilder.forCustomers(uriInfo).toString())).build())
                .build();
    }

    public JsonObject buildCustomer(Client customer, UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("name", customer.getUserName())
                .add("company", customer.getCompany())
                .add("phone number", customer.getPhone() == null ? "" : customer.getPhone())
                .add("your id", customer.getId())
                .add("links",
                        Json.createObjectBuilder()
                                .add("self", linkBuilder.forCustomer(customer.getId(), uriInfo).toString())
                                .add("actions",
                                        Json.createArrayBuilder().add(Json.createObjectBuilder()
                                                .add("name", "add product")
                                                .add("method", HttpMethod.POST)
                                                .add("href", linkBuilder.forAddProduct(uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "productName").add("type", "STRING").add("required", true))
                                                        .add(Json.createObjectBuilder().add("name", "clientId").add("type", "STRING").add("required", true))
                                                        .add(Json.createObjectBuilder().add("name", "description").add("Type", "STRING")).build())))
                                .build())
                .build();
    }

    public JsonObject buildProduct(Product p, UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("id", String.valueOf(p.getId()))
                .add("name", p.getName())
                .add("description", p.getDescription())
                .add("links",
                        Json.createObjectBuilder()
                                .add("self", linkBuilder.forProduct(p.getId(), uriInfo).toString())
                                .add("actions",
                                        Json.createArrayBuilder().add(Json.createObjectBuilder()
                                                .add("name", "add license")
                                                .add("method", HttpMethod.POST)
                                                .add("href", linkBuilder.forAddLicense(uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "productId").add("type", "STRING").add("required", true))
                                                        .add(Json.createObjectBuilder().add("name", "limitDate").add("type", "STRING").add("required", true))
                                                        .build())))
                                .build()).build();
    }

    public JsonObjectBuilder forProducts(Product p, UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("id", p.getId())
                .add("name", p.getName())
                .add("description", p.getDescription() == null ? "" : p.getDescription())
                .add("links",
                        Json.createObjectBuilder()
                                .add("self", linkBuilder.forCustomerProducts(p.getId(), uriInfo).toString())
                                .add("action",
                                        Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", "update Product")
                                                .add("method", HttpMethod.PUT)
                                                .add("href", linkBuilder.forProduct(p.getId(), uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "productName").add("type", "STRING").add("required", true))
                                                        .add(Json.createObjectBuilder().add("name", "description").add("type", "STRING").add("required", true))
                                                        .build())
                                        ).add(Json.createObjectBuilder().add("name", "delete Product")
                                                .add("method", HttpMethod.DELETE)
                                                .add("href", linkBuilder.forProduct(p.getId(), uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "productId").add("type", "STRING").add("required", true))
                                                        .build())
                                                .build())));
    }

    public JsonObjectBuilder forLicense(Licenza l, UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("id", l.getId())
                .add("createdDate", l.getCreatedDate().toString())
                .add("dueDate", l.getLimitDate().toString())
                .add("lastRenew", l.getLastRenew().toString())
                .add("guid", l.getGuid())
                .add("status", l.getStatus().toString())
                .add("links",
                        Json.createObjectBuilder()
                                .add("self", linkBuilder.forLicense(l.getId(), uriInfo).toString())
                                .add("action",
                                        Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", "update License")
                                                .add("method", HttpMethod.PUT)
                                                .add("href", linkBuilder.forLicense(l.getId(), uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "licenseId").add("type", "STRING").add("required", true))
                                                        .add(Json.createObjectBuilder().add("name", "numberOfDays").add("type", "STRING").add("required", true))
                                                        .add(Json.createObjectBuilder().add("name", "onExpired").add("type", "BOOLEAN"))
                                                        .build())
                                        ).add(Json.createObjectBuilder().add("name", "delete license")
                                                .add("method", HttpMethod.DELETE)
                                                .add("href", linkBuilder.forProduct(l.getId(), uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "licenseId").add("type", "STRING").add("required", true))
                                                        .build())
                                                .build())));

    }

    public JsonObjectBuilder forCustomers(Client customer, UriInfo uriInfo) {

        return Json.createObjectBuilder()
                .add("name", customer.getUserName())
                .add("company", customer.getCompany())
                .add("phone number", customer.getPhone() == null ? "" : customer.getPhone())
                .add("your id", customer.getId())
                .add("links",
                        Json.createObjectBuilder()
                                .add("self", linkBuilder.forCustomer(customer.getId(), uriInfo).toString())
                                .add("action",
                                        Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", "update Customer")
                                                .add("method", HttpMethod.PUT)
                                                .add("href", linkBuilder.forProduct(customer.getId(), uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "name").add("type", "STRING"))
                                                        .add(Json.createObjectBuilder().add("name", "company").add("type", "STRING"))
                                                        .add(Json.createObjectBuilder().add("name", "phone").add("type", "STRING"))
                                                        .add(Json.createObjectBuilder().add("name", "password").add("type", "STRING"))
                                                        .build())
                                        ).add(Json.createObjectBuilder().add("name", "delete customer")
                                                .add("method", HttpMethod.DELETE)
                                                .add("href", linkBuilder.forCustomer(customer.getId(), uriInfo).toString())
                                                .add("type", MediaType.APPLICATION_JSON).add("fields",
                                                Json.createArrayBuilder()
                                                        .add(Json.createObjectBuilder().add("name", "id").add("type", "STRING").add("required", true))
                                                        .build())
                                                .build())));
    }

}
