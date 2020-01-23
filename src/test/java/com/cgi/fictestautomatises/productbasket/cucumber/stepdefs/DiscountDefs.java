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



    @Given("a valid Basket and a discount code  {string}")
    public void a_valid_Basket_and_a_discount_code(String code) {
        this.discountcode = code;
        this.discountCodeDTO = new DiscountCodeDTO();
        this.discountCodeDTO.setCode(code);
        this.discountCodeDTO.setId(1L);
        if (code.equals("TenPercent")) {
            this.discountCodeDTO.setDiscount(10F);
        } else if (code.equals("Twenty")) {
            this.discountCodeDTO.setDiscount(20F);
        } else {
            this.discountCodeDTO.setDiscount(0F);
        }
        this.discountCodeDTO = this.discountCodeService.save(this.discountCodeDTO);

        this.basketDTO = new BasketDTO();
        this.basketDTO.setId(BASKET_ID);
        this.basketDTO.setCreationDate(LocalDate.now());
        this.basketDTO.setTotalPrice(0.F);
        this.basketDTO = basketService.save(basketDTO);
    }

    @When("I call save the discount code into the basket")
    public void i_call_DiscountCodeService_save_function() {
        this.basketService.addDiscountCode(this.basketDTO.getId(), this.discountcode );
    }

    @Then("the discount code should be saved into the Basket")
    public void the_discount_code_should_be_saved_into_the_Basket() {
        Optional<BasketDTO> basketDTOOptional = basketService.findOne(this.basketDTO.getId());
        if (basketDTOOptional.isPresent()) {
            Set<DiscountCodeDTO> discountCodeDTOSet = basketDTOOptional.get().getDiscountCodes();
            assertThat(discountCodeDTOSet.size()).isEqualTo(1);
        } else {
            assert (false);
        }
    }


    @Given("a BasketDTO that contains the DiscountCode {string} and a Product with id {int} with a price of {int}")
    public void a_BasketDTO_that_contains_the_DiscountCode_and_a_Product_with_id_with_a_price_of(String string, Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the user save the basket")
    public void the_user_save_the_basket() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the total price of the basket must be {int}")
    public void the_total_price_of_the_basket_must_be(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

}
