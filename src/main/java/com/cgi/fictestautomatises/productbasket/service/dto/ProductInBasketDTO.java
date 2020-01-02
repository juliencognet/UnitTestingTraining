package com.cgi.fictestautomatises.productbasket.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cgi.fictestautomatises.productbasket.domain.ProductInBasket} entity.
 */
public class ProductInBasketDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;


    private Long productId;

    private Long basketId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductInBasketDTO productInBasketDTO = (ProductInBasketDTO) o;
        if (productInBasketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productInBasketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductInBasketDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", productId=" + getProductId() +
            ", basketId=" + getBasketId() +
            "}";
    }
}
