/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.business.licenza.boundary;

import com.petitpapa.business.licenza.entity.Licenza;
import com.petitpapa.utilities.GenericDao;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ufficio
 */
public class LicenceDao extends GenericDao<Licenza> {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    public LicenceDao( ) {
        super(Licenza.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
