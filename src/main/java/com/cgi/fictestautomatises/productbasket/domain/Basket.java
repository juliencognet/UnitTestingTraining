package com.cgi.fictestautomatises.productbasket.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Basket.
 */
@Entity
@Table(name = "basket")
public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "basket")
    private Set<ProductInBasket> products = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "basket_discount_codes",
               joinColumns = @JoinColumn(name = "basket_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "discount_codes_id", referencedColumnName = "id"))
    private Set<DiscountCode> discountCodes = new HashSet<>();

    @OneToOne(mappedBy = "basket")
    @JsonIgnore
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public Basket totalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Basket creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Set<ProductInBasket> getProducts() {
        return products;
    }

    public Basket products(Set<ProductInBasket> productInBaskets) {
        this.products = productInBaskets;
        return this;
    }

    public Basket addProducts(ProductInBasket productInBasket) {
        this.products.add(productInBasket);
        productInBasket.setBasket(this);
        return this;
    }

    public Basket removeProducts(ProductInBasket productInBasket) {
        this.products.remove(productInBasket);
        productInBasket.setBasket(null);
        return this;
    }

    public void setProducts(Set<ProductInBasket> productInBaskets) {
        this.products = productInBaskets;
    }

    public Set<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public Basket discountCodes(Set<DiscountCode> discountCodes) {
        this.discountCodes = discountCodes;
        return this;
    }

    public Basket addDiscountCodes(DiscountCode discountCode) {
        this.discountCodes.add(discountCode);
        discountCode.getBaskets().add(this);
        return this;
    }

    public Basket removeDiscountCodes(DiscountCode discountCode) {
        this.discountCodes.remove(discountCode);
        discountCode.getBaskets().remove(this);
        return this;
    }

    public void setDiscountCodes(Set<DiscountCode> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Basket customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Basket)) {
            return false;
        }
        return id != null && id.equals(((Basket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Basket{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
