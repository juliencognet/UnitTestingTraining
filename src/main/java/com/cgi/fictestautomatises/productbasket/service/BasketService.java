package com.cgi.fictestautomatises.productbasket.service;

import com.cgi.fictestautomatises.productbasket.domain.Basket;
import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;
import com.cgi.fictestautomatises.productbasket.repository.BasketRepository;
import com.cgi.fictestautomatises.productbasket.repository.ProductInBasketRepository;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;
import com.cgi.fictestautomatises.productbasket.service.mapper.BasketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Basket}.
 */
@Service
@Transactional
public class BasketService {

    private final Logger log = LoggerFactory.getLogger(BasketService.class);

    private final BasketRepository basketRepository;

    private final ProductInBasketRepository productInBasketRepository;
    private final ProductService productService;

    private final BasketMapper basketMapper;

    public BasketService(BasketRepository basketRepository, BasketMapper basketMapper, ProductInBasketRepository productInBasketRepository, ProductService productService) {
        this.basketRepository = basketRepository;
        this.basketMapper = basketMapper;
        this.productInBasketRepository = productInBasketRepository;
        this.productService = productService;
    }

    /**
     * Save a basket.
     *
     * @param basketDTO the entity to save.
     * @return the persisted entity.
     */
    public BasketDTO save(BasketDTO basketDTO) {
        log.debug("Request to save Basket : {}", basketDTO);
        Basket basket = basketMapper.toEntity(basketDTO);
        basket = basketRepository.save(basket);
        // Recompute basket price
        this.computeBasketPrice(basket.getId());
        return basketMapper.toDto(basket);
    }

    /**
     * Get all the baskets.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BasketDTO> findAll() {
        log.debug("Request to get all Baskets");
        return basketRepository.findAllWithEagerRelationships().stream()
            .map(basketMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the baskets with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BasketDTO> findAllWithEagerRelationships(Pageable pageable) {
        return basketRepository.findAllWithEagerRelationships(pageable).map(basketMapper::toDto);
    }
    

    /**
     * Get one basket by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BasketDTO> findOne(Long id) {
        log.debug("Request to get Basket : {}", id);
        return basketRepository.findOneWithEagerRelationships(id)
            .map(basketMapper::toDto);
    }

    /**
     * Delete the basket by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Basket : {}", id);
        basketRepository.deleteById(id);
    }


    /**
     * Compute price of current basket
     */
    public void computeBasketPrice(Long id){
        // Get current basket
        Basket currentBasket =
            basketRepository
                .findOneWithEagerRelationships(id)
                .orElseThrow(() -> new RuntimeException("No basket with such ID"));

        // Get all products of basket
        List<ProductInBasket> productsInBasket = productInBasketRepository.findAllByBasketId(id);

        // Compute total price as sum of all product lines (unit price * quantity) added to the basket
        Float totalPrice = productsInBasket.stream()
            .map(p ->
                this.productService.findOne(p.getProduct().getId()).get().getUnitPrice()
                    // must call product service to get product data because when product is added to basket as
                    // ProductInBasket, object is nearly empty (excepted ID)
                * p.getQuantity()
            )
            .reduce((price1, price2) -> price1+price2) // total is sum of all lines
            .orElse(0F); // if no product in basket, basket price is 0

        // Set total price
        currentBasket.setTotalPrice(totalPrice);

        // Save current basket
        basketRepository.save(currentBasket);
    }

}
