package com.petitpapa.business.products.entity;

import com.petitpapa.business.customer.entity.Client;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Client> client;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, Integer> version;

}

