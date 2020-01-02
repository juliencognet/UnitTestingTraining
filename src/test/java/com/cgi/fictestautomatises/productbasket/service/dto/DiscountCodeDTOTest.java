package com.cgi.fictestautomatises.productbasket.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fictestautomatises.productbasket.web.rest.TestUtil;

public class DiscountCodeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountCodeDTO.class);
        DiscountCodeDTO discountCodeDTO1 = new DiscountCodeDTO();
        discountCodeDTO1.setId(1L);
        DiscountCodeDTO discountCodeDTO2 = new DiscountCodeDTO();
        assertThat(discountCodeDTO1).isNotEqualTo(discountCodeDTO2);
        discountCodeDTO2.setId(discountCodeDTO1.getId());
        assertThat(discountCodeDTO1).isEqualTo(discountCodeDTO2);
        discountCodeDTO2.setId(2L);
        assertThat(discountCodeDTO1).isNotEqualTo(discountCodeDTO2);
        discountCodeDTO1.setId(null);
        assertThat(discountCodeDTO1).isNotEqualTo(discountCodeDTO2);
    }
}
