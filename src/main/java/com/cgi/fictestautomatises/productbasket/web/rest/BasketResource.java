package com.cgi.fictestautomatises.productbasket.web.rest;

import com.cgi.fictestautomatises.productbasket.service.BasketService;
import com.cgi.fictestautomatises.productbasket.web.rest.errors.BadRequestAlertException;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;

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
 * REST controller for managing {@link com.cgi.fictestautomatises.productbasket.domain.Basket}.
 */
@RestController
@RequestMapping("/api")
public class BasketResource {

    private final Logger log = LoggerFactory.getLogger(BasketResource.class);

    private static final String ENTITY_NAME = "basket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BasketService basketService;

    public BasketResource(BasketService basketService) {
        this.basketService = basketService;
    }

    /**
     * {@code POST  /baskets} : Create a new basket.
     *
     * @param basketDTO the basketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new basketDTO, or with status {@code 400 (Bad Request)} if the basket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/baskets")
    public ResponseEntity<BasketDTO> createBasket(@Valid @RequestBody BasketDTO basketDTO) throws URISyntaxException {
        log.debug("REST request to save Basket : {}", basketDTO);
        if (basketDTO.getId() != null) {
            throw new BadRequestAlertException("A new basket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BasketDTO result = basketService.save(basketDTO);
        return ResponseEntity.created(new URI("/api/baskets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /baskets} : Updates an existing basket.
     *
     * @param basketDTO the basketDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated basketDTO,
     * or with status {@code 400 (Bad Request)} if the basketDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the basketDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/baskets")
    public ResponseEntity<BasketDTO> updateBasket(@Valid @RequestBody BasketDTO basketDTO) throws URISyntaxException {
        log.debug("REST request to update Basket : {}", basketDTO);
        if (basketDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BasketDTO result = basketService.save(basketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, basketDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /baskets} : get all the baskets.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baskets in body.
     */
    @GetMapping("/baskets")
    public List<BasketDTO> getAllBaskets(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Baskets");
        return basketService.findAll();
    }

    /**
     * {@code GET  /baskets/:id} : get the "id" basket.
     *
     * @param id the id of the basketDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the basketDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/baskets/{id}")
    public ResponseEntity<BasketDTO> getBasket(@PathVariable Long id) {
        log.debug("REST request to get Basket : {}", id);
        Optional<BasketDTO> basketDTO = basketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(basketDTO);
    }

    /**
     * {@code DELETE  /baskets/:id} : delete the "id" basket.
     *
     * @param id the id of the basketDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/baskets/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long id) {
        log.debug("REST request to delete Basket : {}", id);
        basketService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
