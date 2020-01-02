package com.cgi.fictestautomatises.productbasket.web.rest;

import com.cgi.fictestautomatises.productbasket.service.DiscountCodeService;
import com.cgi.fictestautomatises.productbasket.web.rest.errors.BadRequestAlertException;
import com.cgi.fictestautomatises.productbasket.service.dto.DiscountCodeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cgi.fictestautomatises.productbasket.domain.DiscountCode}.
 */
@RestController
@RequestMapping("/api")
public class DiscountCodeResource {

    private final Logger log = LoggerFactory.getLogger(DiscountCodeResource.class);

    private static final String ENTITY_NAME = "discountCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountCodeService discountCodeService;

    public DiscountCodeResource(DiscountCodeService discountCodeService) {
        this.discountCodeService = discountCodeService;
    }

    /**
     * {@code POST  /discount-codes} : Create a new discountCode.
     *
     * @param discountCodeDTO the discountCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountCodeDTO, or with status {@code 400 (Bad Request)} if the discountCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discount-codes")
    public ResponseEntity<DiscountCodeDTO> createDiscountCode(@Valid @RequestBody DiscountCodeDTO discountCodeDTO) throws URISyntaxException {
        log.debug("REST request to save DiscountCode : {}", discountCodeDTO);
        if (discountCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new discountCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountCodeDTO result = discountCodeService.save(discountCodeDTO);
        return ResponseEntity.created(new URI("/api/discount-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discount-codes} : Updates an existing discountCode.
     *
     * @param discountCodeDTO the discountCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountCodeDTO,
     * or with status {@code 400 (Bad Request)} if the discountCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discount-codes")
    public ResponseEntity<DiscountCodeDTO> updateDiscountCode(@Valid @RequestBody DiscountCodeDTO discountCodeDTO) throws URISyntaxException {
        log.debug("REST request to update DiscountCode : {}", discountCodeDTO);
        if (discountCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountCodeDTO result = discountCodeService.save(discountCodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discountCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discount-codes} : get all the discountCodes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discountCodes in body.
     */
    @GetMapping("/discount-codes")
    public List<DiscountCodeDTO> getAllDiscountCodes() {
        log.debug("REST request to get all DiscountCodes");
        return discountCodeService.findAll();
    }

    /**
     * {@code GET  /discount-codes/:id} : get the "id" discountCode.
     *
     * @param id the id of the discountCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discount-codes/{id}")
    public ResponseEntity<DiscountCodeDTO> getDiscountCode(@PathVariable Long id) {
        log.debug("REST request to get DiscountCode : {}", id);
        Optional<DiscountCodeDTO> discountCodeDTO = discountCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountCodeDTO);
    }

    /**
     * {@code DELETE  /discount-codes/:id} : delete the "id" discountCode.
     *
     * @param id the id of the discountCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discount-codes/{id}")
    public ResponseEntity<Void> deleteDiscountCode(@PathVariable Long id) {
        log.debug("REST request to delete DiscountCode : {}", id);
        discountCodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
