/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petitpapa.business.licenza.entity;

import com.petitpapa.business.products.entity.Product;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ufficio
 */
@Entity
public class Licenza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;

    @Version
    @Column
    private int version;

    @Column
    private LocalDate createdDate = LocalDate.now();

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_fk", nullable = false)
    private Product product;

    @NotNull
    private LocalDate limitDate;

    private LocalDate lastRenew;
    
    private String guid;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Licenza() {
    }

    @PrePersist
    public void calculateDueDate() {
      //  this.limitDate = createdDate.plusMonths(1);
        lastRenew = createdDate;
        status = Status.VALID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void renewLicenceDate(int numberOfDays, boolean onExpiredDate) {
        if(onExpiredDate){
            limitDate = lastRenew.plusDays(numberOfDays);
            lastRenew =  LocalDate.now();
        }
        else{
            lastRenew = LocalDate.now();
            limitDate = lastRenew.plusDays(numberOfDays);
        }
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public LocalDate getLastRenew() {
        return lastRenew;
    }

    public void setLastRenew(LocalDate lastRenew) {
        this.lastRenew = lastRenew;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Licenza other = (Licenza) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Licenza{" + "id=" + id + ", createdDate="
                + createdDate + ", limitDate=" + limitDate + ", lastRenew=" + lastRenew + ", guid=" + guid + '}';
    }

}
