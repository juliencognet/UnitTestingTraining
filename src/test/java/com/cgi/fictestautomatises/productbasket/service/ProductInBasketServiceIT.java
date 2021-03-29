package com.cgi.fictestautomatises.productbasket.service;

import com.cgi.fictestautomatises.productbasket.FicTestsAutomatisesApp;
import com.cgi.fictestautomatises.productbasket.domain.Product;
import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductInBasketDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

    @Autowired
    private ProductService productService;


    private BasketDTO basketDTO;


    @BeforeEach
    public void init() {

        basketDTO = new BasketDTO();
        basketDTO.setId(DEFAULT_BASKET_ID);
        basketDTO.setCreationDate(LocalDate.now());
        basketDTO.setTotalPrice(0.F);
        this.basketDTO = basketService.save(basketDTO);
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
        productDTO = productService.save(productDTO);

        // A productInBasket
        ProductInBasketDTO productInBasketDTO = new ProductInBasketDTO();
        productInBasketDTO.setProduct(productDTO);
        productInBasketDTO.setBasketId(this.basketDTO.getId());
        productInBasketDTO.setId(DEFAULT_PRODUCT_IN_BASKET_ID);
        productInBasketDTO.setQuantity(1);
        productInBasketDTO.setProductId(productDTO.getId());


        /**
         *  ------  WHEN  --------------
         */

        // Add a first time the product
        ProductInBasketDTO productInDTOAfterFirstAddition = productInBasketService.add(productInBasketDTO);

        // Add a second time the product
        ProductInBasketDTO productInDTOAfterSecondAddition = productInBasketService.add(productInBasketDTO);

        List<ProductInBasketDTO> productsInBasketDTO = productInBasketService.findAll();

        /**
         *  ------  THEN  --------------
         */
        //Test that only one product is declared in the basket
        assertThat(productsInBasketDTO.size()).isEqualTo(1);

        //Test that the quantity of the product is equal to 2
        assertThat(productsInBasketDTO.get(0).getQuantity()).isEqualTo(2);


    }
}
