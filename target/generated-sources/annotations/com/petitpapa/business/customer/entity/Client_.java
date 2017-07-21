package com.petitpapa.business.customer.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, String> password;
	public static volatile SingularAttribute<Client, String> phone;
	public static volatile SingularAttribute<Client, String> company;
	public static volatile SingularAttribute<Client, Long> id;
	public static volatile SingularAttribute<Client, String> userName;
	public static volatile SingularAttribute<Client, Integer> version;

}

