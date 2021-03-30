package com.cgi.fictestautomatises.productbasket.cucumber.stepdefs;


import com.cgi.fictestautomatises.productbasket.service.BasketService;
import com.cgi.fictestautomatises.productbasket.service.DiscountCodeService;
import com.cgi.fictestautomatises.productbasket.service.dto.BasketDTO;
import com.cgi.fictestautomatises.productbasket.service.dto.DiscountCodeDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class DiscountDefs {

    @Autowired
    BasketService basketService;

    @Autowired
    DiscountCodeService discountCodeService;


    private static final Long BASKET_ID = 1L;


    private BasketDTO basketDTO;
    private DiscountCodeDTO discountCodeDTO;
    private String discountcode;




    @Given("a discount code name {string} which provides a discount of {double} per cent")
    public void a_discount_code_name_which_provides_a_discount_of_per_cent(String string, Double double1) {




    }

    @Given("an empty product basket")
    public void an_empty_product_basket() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the user adds a product with a unit price of {int} dollars")
    public void the_user_adds_a_product_with_a_unit_price_of_dollars(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the total price of the basket must be {int}")
    public void the_total_price_of_the_basket_must_be(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

}
