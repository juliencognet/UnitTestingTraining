package com.cgi.fictestautomatises.productbasket.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fictestautomatises.productbasket.web.rest.TestUtil;

public class ProductInBasketTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInBasket.class);
        ProductInBasket productInBasket1 = new ProductInBasket();
        productInBasket1.setId(1L);
        ProductInBasket productInBasket2 = new ProductInBasket();
        productInBasket2.setId(productInBasket1.getId());
        assertThat(productInBasket1).isEqualTo(productInBasket2);
        productInBasket2.setId(2L);
        assertThat(productInBasket1).isNotEqualTo(productInBasket2);
        productInBasket1.setId(null);
        assertThat(productInBasket1).isNotEqualTo(productInBasket2);
    }
}
