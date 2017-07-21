package com.petitpapa.business.licenza.entity;

import com.petitpapa.business.products.entity.Product;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Licenza.class)
public abstract class Licenza_ {

	public static volatile SingularAttribute<Licenza, Product> product;
	public static volatile SingularAttribute<Licenza, LocalDate> createdDate;
	public static volatile SingularAttribute<Licenza, LocalDate> lastRenew;
	public static volatile SingularAttribute<Licenza, LocalDate> limitDate;
	public static volatile SingularAttribute<Licenza, String> guid;
	public static volatile SingularAttribute<Licenza, Long> id;
	public static volatile SingularAttribute<Licenza, Integer> version;
	public static volatile SingularAttribute<Licenza, Status> status;

}

