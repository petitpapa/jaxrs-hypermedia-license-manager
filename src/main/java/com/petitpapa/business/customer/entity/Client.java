/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.business.customer.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author Ufficio
 */
@NamedQueries({
    @NamedQuery(name = "findCustomerByName", query = "select c from Client c where c.userName = :userName")
    ,
    @NamedQuery(name = "findCustomerByNameAndLogin", query = "select c from Client c where c.userName = :userName and c.password = :password")
})
@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private int version;

    @NotNull
    @Column(nullable = false)
    @Size(min = 2, max = 55)
    private String company;

    @NotNull
    @Column(unique = true, length = 100, nullable = false)
    @Size(max = 100)
    private String userName;

    @Column
    private String phone;

    @Column(length = 256, nullable = false)
    @NotNull
    @Size(min = 1, max = 256)
    private String password;

    public Client() {
    }

    public Client(String name, String company, String phone, String password) {
        this.userName = name;
        this.company = company;
        this.phone = phone;
        this.password = password;
    }

    @PrePersist
    public void digestPassword() {
        try {
            password = digestPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String digestPassword(String plainPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] encodeBytes = md.digest(plainPassword.getBytes());
        return Base64.getEncoder().encodeToString(encodeBytes);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = digestPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.userName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Client other = (Client) obj;
        if (!Objects.equals(this.userName, other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Client{" + "company=" + company + ", name=" + userName + ", phone=" + phone + '}';
    }

}
