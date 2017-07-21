package com.petitpapa.business;

import com.petitpapa.business.customer.boundary.CustomersResource;
import com.petitpapa.business.licenza.boundary.LicenceResource;
import com.petitpapa.business.products.boundary.ProductsResource;
import java.net.URI;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Alseny Cissé
 */
public class LinkBuilder {

    private URI createResourceUri(Class<?> resourceClass, String method, long id, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method)
                .build(id);
    }

    URI forProducts(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(ProductsResource.class).build();
    }

    URI forCustomers(UriInfo ui) {
        return ui.getBaseUriBuilder().path(CustomersResource.class).build();
    }

    URI forCustomerProducts(long customerId, UriInfo uriInfo) {
        return createResourceUri(CustomersResource.class, "getCustomer", customerId, uriInfo);
    }

    URI forCustomer(long customerId, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(CustomersResource.class).path(CustomersResource.class, "getCustomer").build(customerId);
    }

    URI forCustomerProducts(String id, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(CustomersResource.class).path(CustomersResource.class, "products").build(id);
    }

    URI forAddProduct(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(ProductsResource.class).build();
    }

    URI forLicense(long licenseId, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(LicenceResource.class).path(LicenceResource.class, "getLicense").build(licenseId);
    }

    URI forProduct(long id, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(ProductsResource.class).path(ProductsResource.class, "getProduct").build(id);
    }

    URI forAddLicense(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(LicenceResource.class).build();
    }

}
