package com.cgi.fictestautomatises.productbasket.repository;

import com.cgi.fictestautomatises.productbasket.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
