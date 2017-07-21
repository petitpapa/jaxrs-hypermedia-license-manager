package com.petitpapa.business.products.boundary;

import com.petitpapa.business.products.entity.Product;
import com.petitpapa.utilities.GenericDao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author petitpapa
 */
public class ProductDao extends GenericDao<Product> {

    @PersistenceContext
    private EntityManager entityManager;

    public ProductDao() {
        super(Product.class);
    }

    public void addProduct(Product p) {
        create(p);
    }

    public boolean doesProductExists(@NotNull String name) {
        TypedQuery<Product> typedQuery = entityManager.createNamedQuery("findProductByName", Product.class);
        typedQuery.setParameter("name", name);
        try {
            typedQuery.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Product> getCustomerProduct(long id) {
        return getEntityManager().createNamedQuery("findProductByClientId", Product.class)
                .setParameter("id", id).getResultList();
    }
}
