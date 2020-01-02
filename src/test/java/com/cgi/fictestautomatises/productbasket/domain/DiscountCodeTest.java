package com.cgi.fictestautomatises.productbasket.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fictestautomatises.productbasket.web.rest.TestUtil;

public class DiscountCodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountCode.class);
        DiscountCode discountCode1 = new DiscountCode();
        discountCode1.setId(1L);
        DiscountCode discountCode2 = new DiscountCode();
        discountCode2.setId(discountCode1.getId());
        assertThat(discountCode1).isEqualTo(discountCode2);
        discountCode2.setId(2L);
        assertThat(discountCode1).isNotEqualTo(discountCode2);
        discountCode1.setId(null);
        assertThat(discountCode1).isNotEqualTo(discountCode2);
    }
}
