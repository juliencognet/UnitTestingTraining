package com.cgi.fictestautomatises.productbasket.service.mapper;

import com.cgi.fictestautomatises.productbasket.domain.*;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductInBasketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductInBasket} and its DTO {@link ProductInBasketDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, BasketMapper.class})
public interface ProductInBasketMapper extends EntityMapper<ProductInBasketDTO, ProductInBasket> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "basket.id", target = "basketId")
    ProductInBasketDTO toDto(ProductInBasket productInBasket);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "basketId", target = "basket")
    ProductInBasket toEntity(ProductInBasketDTO productInBasketDTO);

    default ProductInBasket fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductInBasket productInBasket = new ProductInBasket();
        productInBasket.setId(id);
        return productInBasket;
    }
}
