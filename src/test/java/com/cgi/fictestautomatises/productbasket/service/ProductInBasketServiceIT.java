package com.cgi.fictestautomatises.productbasket.service;

import com.cgi.fictestautomatises.productbasket.FicTestsAutomatisesApp;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductInBasketDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link UserService}.
 */
@SpringBootTest(classes = FicTestsAutomatisesApp.class)
@Transactional
public class ProductInBasketServiceIT {

    private static final long DEFAULT_BASKET_ID = 1;

    /**
     * PRODUCT1 Characteristics
     */
    private static final long DEFAULT_PRODUCT_ID = 1;
    private static final float DEFAULT_PRODUCT_PRICE = 11;
    private static final String DEFAULT_PRODUCT_NAME = "test_product";

    private static final long DEFAULT_PRODUCT_IN_BASKET_ID = 1;


    @Autowired
    private ProductInBasketService productInBasketService;

    @Autowired
    private BasketService basketService;


    private BasketDTO basketDTO;


    @BeforeEach
    public void init() {

        this.basketDTO = new BasketDTO();
        this.basketDTO.setId(DEFAULT_BASKET_ID);
    }


    @Test
    @Transactional
    public void assertThatAddingAnExistingProductIncreaseQuantity() {
        /**
         *  ------  GIVEN  --------------
         */

        // A product
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(DEFAULT_PRODUCT_ID);
        productDTO.setUnitPrice(DEFAULT_PRODUCT_PRICE);
        productDTO.setProductName(DEFAULT_PRODUCT_NAME);

        BasketDTO basketDTO = new BasketDTO();
        BasketDTO newBasketDTO = basketService.save(basketDTO);

        // A productInBasket
        ProductInBasketDTO productInBasketDTO = new ProductInBasketDTO();
        productInBasketDTO.setProduct(productDTO);
        productInBasketDTO.setBasketId(newBasketDTO.getId());
        productInBasketDTO.setId(DEFAULT_PRODUCT_IN_BASKET_ID);
        productInBasketDTO.setQuantity(1);
        productInBasketDTO.setProductId(productDTO.getId());


        /**
         *  ------  WHEN  --------------
         */

        // Add a first time the product
        ProductInBasketDTO productDTOAfterFirstAddition = productInBasketService.add(productInBasketDTO);

        // Add a second time the product
        ProductInBasketDTO productDTOAfterSecondAddition = productInBasketService.add(productInBasketDTO);

        /**
         *  ------  THEN  --------------
         */
        assertThat(productDTOAfterSecondAddition.getQuantity()).isEqualTo(2);


    }
}
