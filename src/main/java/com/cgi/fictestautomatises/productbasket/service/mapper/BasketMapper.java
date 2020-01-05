package com.cgi.fictestautomatises.productbasket.service.mapper;

import com.cgi.fictestautomatises.productbasket.domain.*;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Basket} and its DTO {@link BasketDTO}.
 */
@Mapper(componentModel = "spring", uses = {DiscountCodeMapper.class})
public interface BasketMapper extends EntityMapper<BasketDTO, Basket> {

    @Mapping(target = "removeProducts", ignore = true)
    @Mapping(target = "removeDiscountCodes", ignore = true)
    @Mapping(target = "customer", ignore = true)
    Basket toEntity(BasketDTO basketDTO);

    default Basket fromId(Long id) {
        if (id == null) {
            return null;
        }
        Basket basket = new Basket();
        basket.setId(id);
        return basket;
    }
}
