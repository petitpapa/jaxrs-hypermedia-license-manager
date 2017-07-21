package com.petitpapa.business.customer.boundary;

import com.petitpapa.business.customer.entity.Client;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author petitpapa
 */
public class CustomerService {

    @Inject
    private CustomerDao dao;

    Client getCustomer(long id) {
        return dao.find(id);
    }

    boolean persistCustomer(Client client) {
        boolean created = false;
        try {
            dao.getEntityManager().createNamedQuery("findCustomerByName", Client.class).setParameter("userName", client.getUserName()).getSingleResult();
        } catch (NoResultException e) {
            dao.create(client);
            created = true;
        }
        return created;
    }

}
