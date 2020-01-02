package com.cgi.fictestautomatises.productbasket.service.mapper;

import com.cgi.fictestautomatises.productbasket.domain.*;
import com.cgi.fictestautomatises.productbasket.service.dto.DiscountCodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiscountCode} and its DTO {@link DiscountCodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiscountCodeMapper extends EntityMapper<DiscountCodeDTO, DiscountCode> {


    @Mapping(target = "baskets", ignore = true)
    @Mapping(target = "removeBaskets", ignore = true)
    DiscountCode toEntity(DiscountCodeDTO discountCodeDTO);

    default DiscountCode fromId(Long id) {
        if (id == null) {
            return null;
        }
        DiscountCode discountCode = new DiscountCode();
        discountCode.setId(id);
        return discountCode;
    }
}
