package com.cgi.fictestautomatises.productbasket.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fictestautomatises.productbasket.web.rest.TestUtil;

public class ProductInBasketDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInBasketDTO.class);
        ProductInBasketDTO productInBasketDTO1 = new ProductInBasketDTO();
        productInBasketDTO1.setId(1L);
        ProductInBasketDTO productInBasketDTO2 = new ProductInBasketDTO();
        assertThat(productInBasketDTO1).isNotEqualTo(productInBasketDTO2);
        productInBasketDTO2.setId(productInBasketDTO1.getId());
        assertThat(productInBasketDTO1).isEqualTo(productInBasketDTO2);
        productInBasketDTO2.setId(2L);
        assertThat(productInBasketDTO1).isNotEqualTo(productInBasketDTO2);
        productInBasketDTO1.setId(null);
        assertThat(productInBasketDTO1).isNotEqualTo(productInBasketDTO2);
    }
}
