package com.cgi.fictestautomatises.productbasket.cucumber.stepdefs;


import com.cgi.fictestautomatises.productbasket.domain.Basket;
import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;
import com.cgi.fictestautomatises.productbasket.service.*;
import com.cgi.fictestautomatises.productbasket.service.dto.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class DiscountDefs {

    @Autowired
    BasketService basketService;

    @Autowired
    DiscountCodeService discountCodeService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductInBasketService productInBasketService;

    private static final Long BASKET_ID = 1L;


    private BasketDTO basketDTO;
    private DiscountCodeDTO discountCodeDTO;

    @Given("a discount code name {string} which provides a discount of {double} per cent")
    public void a_discount_code_name_which_provides_a_discount_of_per_cent(String code, Double discount) {
        discountCodeDTO = new DiscountCodeDTO();
        discountCodeDTO.setId(10L);
        discountCodeDTO.setCode(code);
        discountCodeDTO.setDiscount(discount.floatValue());
        discountCodeDTO = discountCodeDTO = discountCodeService.save(discountCodeDTO);
    }

    @Given("an empty product basket")
    public void an_empty_product_basket() {
        basketDTO = new BasketDTO();
        basketDTO.setId(BASKET_ID);
        basketDTO.setCreationDate(LocalDate.now());
        basketDTO.setDiscountCodes(Collections.asSet(discountCodeDTO));
        basketDTO.setTotalPrice(0F);
        basketDTO = basketService.save(basketDTO);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setBasketId(basketDTO.getId());
        customerDTO.setEmail("customer@email.com");
        customerDTO.setFirstName("customer");
        customerDTO.setLastName("name");
        customerDTO = customerService.save(customerDTO);
    }

    @When("the user adds a product with a unit price of {int} dollars")
    public void the_user_adds_a_product_with_a_unit_price_of_dollars(Integer int1) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setProductName("productName");
        productDTO.setCategories("categories");
        productDTO.setEan13BarCode("ean13");
        productDTO.setImageUrl("imageurl");
        productDTO.setBrand("fakeBrand");
        productDTO.setUnitPrice(int1.floatValue());

        productDTO = productService.save(productDTO);

        ProductInBasketDTO productInBasketDTO = new ProductInBasketDTO();
        productInBasketDTO.setProduct(productDTO);
        productInBasketDTO.setProductId(productDTO.getId());
        productInBasketDTO.setQuantity(1);
        productInBasketDTO.setId(1L);
        productInBasketDTO.setBasketId(basketDTO.getId());
        productInBasketDTO = productInBasketService.add(productInBasketDTO);
    }

    @Then("the total price of the basket must be {int}")
    public void the_total_price_of_the_basket_must_be(Integer int1) {
        BasketDTO computedBasked = basketService.findOne(basketDTO.getId()).get();
        assertThat(computedBasked.getTotalPrice()).isEqualTo(int1.floatValue());
    }

}
