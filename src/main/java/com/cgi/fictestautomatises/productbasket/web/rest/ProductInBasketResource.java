package com.cgi.fictestautomatises.productbasket.web.rest;

import com.cgi.fictestautomatises.productbasket.service.ProductInBasketService;
import com.cgi.fictestautomatises.productbasket.web.rest.errors.BadRequestAlertException;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductInBasketDTO;

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
 * REST controller for managing {@link com.cgi.fictestautomatises.productbasket.domain.ProductInBasket}.
 */
@RestController
@RequestMapping("/api")
public class ProductInBasketResource {

    private final Logger log = LoggerFactory.getLogger(ProductInBasketResource.class);

    private static final String ENTITY_NAME = "productInBasket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductInBasketService productInBasketService;

    public ProductInBasketResource(ProductInBasketService productInBasketService) {
        this.productInBasketService = productInBasketService;
    }

    /**
     * {@code POST  /product-in-baskets} : Create a new productInBasket.
     *
     * @param productInBasketDTO the productInBasketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productInBasketDTO, or with status {@code 400 (Bad Request)} if the productInBasket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-in-baskets")
    public ResponseEntity<ProductInBasketDTO> createProductInBasket(@Valid @RequestBody ProductInBasketDTO productInBasketDTO) throws URISyntaxException {
        log.debug("REST request to save ProductInBasket : {}", productInBasketDTO);
        if (productInBasketDTO.getId() != null) {
            throw new BadRequestAlertException("A new productInBasket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductInBasketDTO result = productInBasketService.save(productInBasketDTO);
        return ResponseEntity.created(new URI("/api/product-in-baskets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-in-baskets} : Updates an existing productInBasket.
     *
     * @param productInBasketDTO the productInBasketDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInBasketDTO,
     * or with status {@code 400 (Bad Request)} if the productInBasketDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productInBasketDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-in-baskets")
    public ResponseEntity<ProductInBasketDTO> updateProductInBasket(@Valid @RequestBody ProductInBasketDTO productInBasketDTO) throws URISyntaxException {
        log.debug("REST request to update ProductInBasket : {}", productInBasketDTO);
        if (productInBasketDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductInBasketDTO result = productInBasketService.save(productInBasketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productInBasketDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-in-baskets} : get all the productInBaskets.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productInBaskets in body.
     */
    @GetMapping("/product-in-baskets")
    public List<ProductInBasketDTO> getAllProductInBaskets() {
        log.debug("REST request to get all ProductInBaskets");
        return productInBasketService.findAll();
    }

    /**
     * {@code GET  /product-in-baskets/:id} : get the "id" productInBasket.
     *
     * @param id the id of the productInBasketDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productInBasketDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-in-baskets/{id}")
    public ResponseEntity<ProductInBasketDTO> getProductInBasket(@PathVariable Long id) {
        log.debug("REST request to get ProductInBasket : {}", id);
        Optional<ProductInBasketDTO> productInBasketDTO = productInBasketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productInBasketDTO);
    }

    /**
     * {@code DELETE  /product-in-baskets/:id} : delete the "id" productInBasket.
     *
     * @param id the id of the productInBasketDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-in-baskets/{id}")
    public ResponseEntity<Void> deleteProductInBasket(@PathVariable Long id) {
        log.debug("REST request to delete ProductInBasket : {}", id);
        productInBasketService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
