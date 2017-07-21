/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.utilities;

import java.util.List;
import javax.persistence.EntityManager;
import static javax.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;

/**
 *
 * @author petitpapa
 */
public abstract class GenericDao<T> {

    private final Class<T> entityClass;

    public GenericDao(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(long id) {
        return getEntityManager().find(entityClass, id);
    }

    public void joinTransaction() {
        if (!getEntityManager().isJoinedToTransaction()) {
            getEntityManager().joinTransaction();
        }
    }

    public void checkVersion(T entity) {
        getEntityManager().lock(entity, OPTIMISTIC_FORCE_INCREMENT);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
