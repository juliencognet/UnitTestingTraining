package com.cgi.fictestautomatises.productbasket.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DiscountCode.
 */
@Entity
@Table(name = "discount_code")
public class DiscountCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "discount", nullable = false)
    private Float discount;

    @ManyToMany(mappedBy = "discountCodes")
    @JsonIgnore
    private Set<Basket> baskets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public DiscountCode code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getDiscount() {
        return discount;
    }

    public DiscountCode discount(Float discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Set<Basket> getBaskets() {
        return baskets;
    }

    public DiscountCode baskets(Set<Basket> baskets) {
        this.baskets = baskets;
        return this;
    }

    public DiscountCode addBaskets(Basket basket) {
        this.baskets.add(basket);
        basket.getDiscountCodes().add(this);
        return this;
    }

    public DiscountCode removeBaskets(Basket basket) {
        this.baskets.remove(basket);
        basket.getDiscountCodes().remove(this);
        return this;
    }

    public void setBaskets(Set<Basket> baskets) {
        this.baskets = baskets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountCode)) {
            return false;
        }
        return id != null && id.equals(((DiscountCode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiscountCode{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
