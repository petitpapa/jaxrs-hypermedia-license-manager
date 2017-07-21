package com.petitpapa.business.customer.boundary;

import com.petitpapa.business.customer.entity.Client;
import com.petitpapa.utilities.GenericDao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author petitpapa
 */
@Stateless
public class CustomerDao extends GenericDao<Client> {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerDao() {
        super(Client.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Client findCustomer(@NotNull String userName, @NotNull String password) {
        TypedQuery<Client> tq = getEntityManager().createNamedQuery("findCustomerByNameAndLogin", Client.class);
        tq.setParameter("userName", userName);
        tq.setParameter("password", password);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean doesCustomerExists(@NotNull String name) {
        TypedQuery<Client> typedQuery = entityManager.createNamedQuery("findCustomerByName", Client.class);
        typedQuery.setParameter("userName", name);
        try {
            typedQuery.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
