package com.cgi.fictestautomatises.productbasket.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cgi.fictestautomatises.productbasket.domain.DiscountCode} entity.
 */
public class DiscountCodeDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Float discount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiscountCodeDTO discountCodeDTO = (DiscountCodeDTO) o;
        if (discountCodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discountCodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiscountCodeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
