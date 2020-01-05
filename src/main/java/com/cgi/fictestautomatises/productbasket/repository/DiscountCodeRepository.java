package com.cgi.fictestautomatises.productbasket.repository;

import com.cgi.fictestautomatises.productbasket.domain.DiscountCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the DiscountCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {

    Optional<DiscountCode> findOneByCode(String code);
}
