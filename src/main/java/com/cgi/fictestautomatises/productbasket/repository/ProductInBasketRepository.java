package com.cgi.fictestautomatises.productbasket.repository;

import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ProductInBasket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductInBasketRepository extends JpaRepository<ProductInBasket, Long> {

    List<ProductInBasket> findAllByBasketId(Long basketId);

}
