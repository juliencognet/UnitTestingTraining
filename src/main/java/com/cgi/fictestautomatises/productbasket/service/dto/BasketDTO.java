package com.cgi.fictestautomatises.productbasket.service.dto;
import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the {@link com.cgi.fictestautomatises.productbasket.domain.Basket} entity.
 */
public class BasketDTO implements Serializable {

    private Long id;

    @NotNull
    private Float totalPrice;

    @NotNull
    private LocalDate creationDate;


    private Set<DiscountCodeDTO> discountCodes = new HashSet<>();

    private Set<ProductInBasketDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Set<DiscountCodeDTO> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(Set<DiscountCodeDTO> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public Set<ProductInBasketDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductInBasketDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasketDTO basketDTO = (BasketDTO) o;
        if (basketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), basketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BasketDTO{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
