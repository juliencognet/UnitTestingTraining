package com.cgi.fictestautomatises.productbasket.service;

import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;
import com.cgi.fictestautomatises.productbasket.repository.ProductInBasketRepository;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductInBasketDTO;
import com.cgi.fictestautomatises.productbasket.service.mapper.ProductInBasketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductInBasket}.
 */
@Service
@Transactional
public class ProductInBasketService {

    private final Logger log = LoggerFactory.getLogger(ProductInBasketService.class);

    private final ProductInBasketRepository productInBasketRepository;
    private final BasketService basketService;
    private final ProductInBasketMapper productInBasketMapper;

    public ProductInBasketService(ProductInBasketRepository productInBasketRepository, BasketService basketService, ProductInBasketMapper productInBasketMapper) {
        this.productInBasketRepository = productInBasketRepository;
        this.basketService = basketService;
        this.productInBasketMapper = productInBasketMapper;
    }

    /**
     * Save a productInBasket.
     *
     * @param productInBasketDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductInBasketDTO save(ProductInBasketDTO productInBasketDTO) {
        log.debug("Request to save ProductInBasket : {}", productInBasketDTO);
        ProductInBasket productInBasket = productInBasketMapper.toEntity(productInBasketDTO);
        productInBasket = productInBasketRepository.save(productInBasket);

        // Recompute basket price
        this.basketService.computeBasketPrice(productInBasket.getBasket().getId());

        return productInBasketMapper.toDto(productInBasket);
    }

    /**
     * Get all the productInBaskets.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductInBasketDTO> findAll() {
        log.debug("Request to get all ProductInBaskets");
        return productInBasketRepository.findAll().stream()
            .map(productInBasketMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productInBasket by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductInBasketDTO> findOne(Long id) {
        log.debug("Request to get ProductInBasket : {}", id);
        return productInBasketRepository.findById(id)
            .map(productInBasketMapper::toDto);
    }

    /**
     * Delete the productInBasket by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductInBasket : {}", id);
        // First retrieve basket ID
        ProductInBasket productInBasket = productInBasketRepository.findById(id)
            .orElseThrow(()->new RuntimeException("No product in basket with such id"));
        // Then delete the product in basket
        productInBasketRepository.deleteById(id);
        // Compute the price of basket
        this.basketService.computeBasketPrice(productInBasket.getBasket().getId());
    }
}
