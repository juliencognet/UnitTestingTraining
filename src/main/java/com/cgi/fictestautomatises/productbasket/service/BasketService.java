package com.cgi.fictestautomatises.productbasket.service;

import com.cgi.fictestautomatises.productbasket.domain.Basket;
import com.cgi.fictestautomatises.productbasket.domain.DiscountCode;
import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;
import com.cgi.fictestautomatises.productbasket.repository.BasketRepository;
import com.cgi.fictestautomatises.productbasket.repository.ProductInBasketRepository;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.DiscountCodeDTO;
import com.cgi.fictestautomatises.productbasket.service.mapper.BasketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    private final DiscountCodeService discountCodeService;

    public BasketService(BasketRepository basketRepository, ProductInBasketRepository productInBasketRepository, ProductService productService, BasketMapper basketMapper, DiscountCodeService discountCodeService) {
        this.basketRepository = basketRepository;
        this.productInBasketRepository = productInBasketRepository;
        this.productService = productService;
        this.basketMapper = basketMapper;
        this.discountCodeService = discountCodeService;
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
    *  Get all the baskets where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BasketDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all baskets where Customer is null");
        return StreamSupport
            .stream(basketRepository.findAll().spliterator(), false)
            .filter(basket -> basket.getCustomer() == null)
            .map(basketMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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

        // Get list of discount codes and keep the best one
        Optional<DiscountCode> bestDiscountCode = currentBasket.getDiscountCodes().stream().max(Comparator.comparing(DiscountCode::getDiscount));
        float discount = 1;
        if (bestDiscountCode.isPresent()){
            discount = 1-bestDiscountCode.get().getDiscount();
        }

        // Compute total price as sum of all product lines (unit price * quantity) added to the basket
        float finalDiscount = discount;
        Float totalPrice = productsInBasket.stream()
            .map(p ->
                this.productService.findOne(p.getProduct().getId()).get().getUnitPrice()
                    // must call product service to get product data because when product is added to basket as
                    // ProductInBasket, object is nearly empty (excepted ID)
                    * p.getQuantity()
                    * finalDiscount
            )
            .reduce((price1, price2) -> price1+price2) // total is sum of all lines
            .orElse(0F); // if no product in basket, basket price is 0

        // Set total price
        currentBasket.setTotalPrice(totalPrice);

        // Save current basket
        basketRepository.save(currentBasket);
    }


    public BasketDTO addDiscountCode(Long id, String discountCodeName){
        //Try to retrieve basket
        Basket basket = basketRepository.getOne(id);

        // Try to retrieve discount Code
        DiscountCode discountCode = discountCodeService.findOneByCode(discountCodeName).orElseThrow(()->new RuntimeException("Discount code "+discountCodeName+" does not exist"));

        // If found, add it
        basket.addDiscountCodes(discountCode);

        // Then save it
        basketRepository.save(basket);

        // Recompute basket price
        computeBasketPrice(id);

        // And return it
        return basketMapper.toDto(basket);
    }

}
