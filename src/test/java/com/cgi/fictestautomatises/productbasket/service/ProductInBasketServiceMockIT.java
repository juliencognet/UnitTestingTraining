package com.cgi.fictestautomatises.productbasket.service;

import com.cgi.fictestautomatises.productbasket.FicTestsAutomatisesApp;
import com.cgi.fictestautomatises.productbasket.domain.Basket;
import com.cgi.fictestautomatises.productbasket.repository.BasketRepository;
import com.cgi.fictestautomatises.productbasket.repository.ProductInBasketRepository;
import com.cgi.fictestautomatises.productbasket.repository.ProductRepository;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductInBasketDTO;
import com.cgi.fictestautomatises.productbasket.service.mapper.ProductInBasketMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;


import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
/**
 * Integration tests for {@link UserService}.
 */
@SpringBootTest(classes = FicTestsAutomatisesApp.class)
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@Transactional
public class ProductInBasketServiceMockIT {

    private static final long DEFAULT_BASKET_ID = 1;

    /**
     * PRODUCT1 Characteristics
     */
    private static final long DEFAULT_PRODUCT_ID = 1;
    private static final float DEFAULT_PRODUCT_PRICE = 11;
    private static final String DEFAULT_PRODUCT_NAME = "test_product";

    private static final long DEFAULT_PRODUCT_IN_BASKET_ID = 1;

    @MockBean
    private BasketService basketService;

    @MockBean
    public ProductInBasketRepository productInBasketRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    @InjectMocks
    private ProductInBasketService productInBasketService;


    private BasketDTO basketDTO;


    @BeforeEach
    public void beforeEachTest() {

        basketDTO = new BasketDTO();
        basketDTO.setId(DEFAULT_BASKET_ID);
        basketDTO.setCreationDate(LocalDate.now());
        basketDTO.setTotalPrice(0.F);
        Mockito.when(this.basketService.save(any( BasketDTO.class ))).thenReturn(basketDTO);
        Mockito.when(this.productInBasketRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        this.basketDTO = this.basketService.save(basketDTO);
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
        ProductInBasketDTO productDTOAfterFirstAddition = productInBasketService.add(productInBasketDTO);
        // Add a second time the product
        ProductInBasketDTO productDTOAfterSecondAddition = productInBasketService.add(productInBasketDTO);

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
